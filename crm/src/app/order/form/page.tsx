"use client";
import React, { useEffect, useState } from "react";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";

import DefaultLayout from "@/components/Layouts/DefaultLayout";

import { Product } from "@/types/product";
import Link from "next/link";
import { CATEGORY_SERVICE, ORDER_SERVICE, UPLOAD_SERVICE } from "@/services/api.service";
import { redirect, useRouter, useSearchParams } from "next/navigation";
import { formatMoney, setField } from "@/services/helpers.service";
import SelectGroupTwo from "@/components/SelectGroup/SelectGroupTwo";
import Loader from "@/components/common/Loader";

const INIT_TRAN: any = {
	name: '',
	price: '',
	order_id: 0,
	product_id: 0,
	status: 2,
	discount: 0,
	user_id: 2,
	quantity: '',
	total_price: '',
};

const INIT_ORDER: any = {
	name: '',
	node: '',
	receiver_name: '',
	receiver_email: '',
	receiver_phone: '',
	receiver_address: '',
	code: '',
	total_discount: 0,
	payment_type: 0,
	category_id: null,
	status: "",
	user_id: 0,
	total_price: 0,
};



const OrderForm: React.FC = () => {

	const [file, setFile] = useState(null);
	const [loading, setLoading] = useState(false);
	const [data, setData]: any = useState(INIT_ORDER);
	const [transaction, setTransaction]: any = useState([INIT_TRAN]);
	const [categories, setCategories]: any = useState([
		{
			id: 1,
			name: 'Xem phim',
		},

		{
			id: 2,
			name: 'Mua hàng hóa',
		},

		{
			id: 3,
			name: 'Đồ uống',
		},

	]);

	const [title, setTitle] = useState('Tạo mới');
	const [id, setId] = useState(0);
	const params = useSearchParams();

	const router = useRouter()


	useEffect(() => {
		let id = Number(params.get('id') || 0);

		setId(id);
		if (id) {
			setTitle('Cập nhật');
			getData(id)
		}

		getCategory();	

	}, [params.get('id')]);


	const getData = async (id: any) => {
		setLoading(true);

		const response: any = await ORDER_SERVICE.show(id);
		setLoading(false);

		if (response?.status == 'success') {
			setData(response.data || null);
			let trans = response.data.transactions || [INIT_TRAN];
			setTransaction(trans)
		}
	}

	const getCategory = async () => {
		const response: any = await CATEGORY_SERVICE.getList({page: 1, page_size: 100});
		if (response?.status == 'success') {
			setCategories(response.data || null);
		}
	}

	const changeFile = async (e: any) => {
		e.preventDefault();
		let data = `4- m
		@ . “
		"J Cofơee Kem Sen Tráng
		' ……o—gMJ m…… … m……
		’ HÓA ac… THANH TOÁN
		HD 0005
		_ N ấy 13/0913m7 mo «2 za 54
		; sầu: A 1
		YIN HANG IL & mA ! neu
		Yanư\ Nhu Dam \ 25000 25000
		Chanh DIY 1 20000 20000
		Kem sau… 1 22000 22000
		Kem VunHa 1 22000 22000
		Kem sủ Rvâng 1 22000 22000
		Kem Dưa 1 22000 22000
		Nước Ep Ôl 1 28000 28 000
		…… Ép Thơm 1 26,000 moon
		Dua_
		r.ẹọne e 1ae.uoo
		efAM % HĐ (-zom .a1_eou
		TIEN MẬT 151.200
		Mot năm năm mươi môt ngàn ha năm
		đóng /
		
		`
		if (e.target.files) {
			const response = await UPLOAD_SERVICE.upload_ocr(e.target.files[0]);
			console.log(response?.data);
		}
	}

	const submit = async (e: any) => {
		e.preventDefault();
		let response: any;
		let bodyData: any = data;
		bodyData.transactions = transaction;
		bodyData.total_price = transaction.reduce((newTotal: any, item: any) => {
            newTotal += Number(item.total_price);
			return newTotal;
		}, 0);
		setLoading(true);

		if (id) {
			response =  await ORDER_SERVICE.update(id, bodyData);
		} else {
			response =  await ORDER_SERVICE.store(bodyData);
		}
		setLoading(false);

		if(response?.status == 'success') {
			resetForm()
			router.push('/order');
		}
	}

	const resetForm = () => {
		setData({
			name: '',
			node: '',
			receiver_name: '',
			receiver_email: '',
			receiver_phone: '',
			receiver_address: '',
			code: '',
			total_discount: 0,
			payment_type: 0,
			category_id: null,
			status: "",
			user_id: 0,
			total_price: 0,
		});
		setTransaction([{
			name: '',
			price: '',
			order_id: 0,
			product_id: 0,
			status: 2,
			discount: 0,
			user_id: 2,
			quantity: '',
			total_price: '',
		}])
	}

	const genTotalPrice: any = (index: any) => {
		console.log(index);
		return Number(transaction[index].quantity) * Number(transaction[index].price)
	}


	return (
		<DefaultLayout>
			<Breadcrumb pageName={title} subName="Order" />
			<div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
				<div className="border-b border-stroke px-6.5 py-4 dark:border-strokedark">
					<h3 className="font-medium text-black dark:text-white text-2xl">
						{/* {title} */}
					</h3>
				</div>
				{loading && <Loader className={"bg-opacity-60 bg-white z-50 fixed top-0 left-0 w-full h-full"} />}

				<div className="flex flex-col gap-5.5 p-6.5">
					<form>
						<div className="md:grid md:grid-cols-2 md:gap-4">
							<div className="mb-5">
								<label className="mb-3 text-xl block text-sm font-medium text-black dark:text-white">
									Tên đơn hàng
								</label>
								<input
									type="text"
									placeholder="Tên đơn hàng"
									defaultValue={data.name}
									onChange={e => {
										setField(e?.target?.value, 'name', data, setData);
									}}
									className="w-full rounded-lg border-[1.5px] border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white"
								/>
							</div>
							<div className="mb-5">
								<SelectGroupTwo
									title={'Phân loại'}
									options={categories}
									key_obj={'category_id'}
									value={data.category_id}
									form={data}
									setForm={setData}
								/>
							</div>
						</div>
						<div className="md:grid md:grid-cols-2 md:gap-4">
							<div className="mb-5">
								<label className="mb-3 text-xl block text-sm font-medium text-black dark:text-white">
									Tên khách hàng
								</label>
								<input
									type="text"
									placeholder="Tên khách hàng"
									defaultValue={data.receiver_name}
									onChange={e => {
										setField(e?.target?.value, 'receiver_name', data, setData);
									}}
									className="w-full rounded-lg border-[1.5px] border-primary 
									bg-transparent px-5 py-3 text-black outline-none transition 
									focus:border-primary active:border-primary disabled:cursor-default 
									disabled:bg-whiter dark:bg-form-input dark:text-white text-bold"
								/>
							</div>
							<div className="mb-5">
								<label className="mb-3 text-xl block text-sm font-medium text-black dark:text-white">
									Số ĐT
								</label>
								<input
									type="text"
									placeholder="Số ĐT"
									defaultValue={data.receiver_phone}
									onChange={e => {
										setField(e?.target?.value, 'receiver_phone', data, setData);
									}}
									className="w-full rounded-lg border-[1.5px] border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white"
								/>
							</div>
						</div>
						<div className="md:grid md:grid-cols-2 md:gap-4">
							<div className="mb-5">
								<SelectGroupTwo
									title={'Trạng thái'}
									options={[
										{
											id: 1,
											name: 'Chưa thanh toán'
										},
										{
											id: 2,
											name: 'Đã thanh toán'
										}
									]}
									key_obj={'status'}
									value={data.status}
									form={data}
									setForm={setData}
								/>

							</div>
							<div className="mb-5">
								<SelectGroupTwo
									title={'Phương thức thanh toán'}
									options={[
										{
											id: 1,
											name: 'Online'
										},
										{
											id: 2,
											name: 'Tiền mặt'
										}
									]}
									key_obj={'payment_type'}
									value={data.payment_type}
									form={data}
									setForm={setData}
								/>
							</div>
						</div>
						{!id && <div className="mb-5">
							<label className="mb-3 text-xl block text-sm font-medium text-black dark:text-white">
								Choose file
							</label>
							<input
								type="file"
								className="w-full cursor-pointer rounded-lg border-[1.5px] 
								border-stroke bg-transparent outline-none transition 
								file:mr-5 file:border-collapse file:cursor-pointer 
								file:border-0 file:border-r file:border-solid 
								file:border-stroke file:bg-whiter file:px-5 
								file:py-3 file:hover:bg-primary file:hover:bg-opacity-10 
								focus:border-primary active:border-primary disabled:cursor-default 
								disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input 
								dark:file:border-form-strokedark dark:file:bg-white/30 dark:file:text-white 
								dark:focus:border-primary"
								onChange={(e) => changeFile(e)}
							/>
						</div>}
						<div className="mb-5">
							<h3 className="font-medium text-xl text-black dark:text-white mb-3">
								Hóa đơn thanh toán
							</h3>
							<div className="grid grid-cols-6 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5">
								<div className="col-span-3">
									<p className="font-medium">Product Name</p>
								</div>
								<div className="col-span-2">
									<p className="font-medium text-center">Quantity</p>
								</div>
								<div className="col-span-2">
									<p className="font-medium text-center">Price</p>
								</div>
								<div className="col-span-1">
									<p className="font-medium  text-center">Total</p>
								</div>
							</div>

							{transaction?.length > 0 && transaction.map((product: any, key: any) => (
								<div
									className="grid grid-cols-6 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5"
									key={key}
								>
									<div className="col-span-3">
										<input
											type="text"
											placeholder="Active Input"
											defaultValue={product.name}
											onChange={e => {
												transaction[key].name = e?.target?.value;
												setTransaction(transaction);
											}}
											className="w-full rounded-lg border-[1.5px] border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white"
										/>
									</div>
									<div className="col-span-2 px-2">
										<input
											type="text"
											placeholder="Quantity"
											defaultValue={product.quantity}
											onChange={e => {
												if (e?.target?.value) {
													transaction[key].quantity = Number(e?.target?.value);
													transaction[key].total_price = Number(e?.target?.value) * Number(product.price || 0);
													setTransaction(transaction);
												}

											}}
											className="w-full rounded-lg border-[1.5px] border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white"
										/>
									</div>
									<div className="col-span-2 px-2">
										<input
											type="text"
											placeholder="Active Input"
											defaultValue={product.price}
											onChange={e => {
												if (e?.target?.value) {
													transaction[key].price = e?.target?.value ? Number(e?.target?.value) : '';
													transaction[key].total_price = Number(e?.target?.value) * Number(product.quantity || 0);
												}
												setTransaction(transaction);
											}}
											className="w-full rounded-lg border-[1.5px] border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white"
										/>
									</div>
									<div className="col-span-1 flex items-center justify-center">
										<p className="text-sm  text-center text-black dark:text-white">
											<b>{formatMoney(transaction[key].total_price || 0) }</b>
										</p>
									</div>
								</div>
							))}

							<div className="inline-flex items-center justify-center 
							rounded-md bg-success px-20 py-2 text-center 
							font-medium text-white hover:bg-opacity-90 lg:px-8 xl:px-10"
								onClick={(e) => {
									let obj = {
										name: '',
										price: '',
										order_id: 0,
										product_id: 0,
										status: 2,
										discount: 0,
										user_id: 2,
										quantity: '',
										total_price: '',
									}
									transaction.push(obj);
									setTransaction([...transaction]);
								}}
							>Thêm</div>

						</div>
						<div className="mb-5">
							<div className="grid grid-cols-6 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5">
								<div className="col-span-6">
									<p className="font-medium text-xl">Tổng tiền</p>
								</div>
								<div className="col-span-2">
									<p className="font-medium text-center text-xl">{formatMoney(data.total_price)}</p>
								</div>
							</div>
						</div>
						<div className="flex justify-center">
							<Link href={'/order'} className="inline-flex items-center justify-center rounded-md bg-gray mr-3 px-10 py-4 text-center font-medium hover:bg-gray-900 lg:px-8 xl:px-10">Cancel</Link>
							<button className="inline-flex items-center justify-center 
							rounded-md bg-primary px-10 py-4 text-center 
							font-medium text-white hover:bg-opacity-90 lg:px-8 xl:px-10"
								onClick={(e) => submit(e)}
							>Submit</button>
						</div>
					</form>
				</div>
			</div>
		</DefaultLayout>
	);
};

export default OrderForm;
