"use client";
import React, { useEffect, useState } from "react";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";

import DefaultLayout from "@/components/Layouts/DefaultLayout";

import { Product } from "@/types/product";
import Link from "next/link";
import { ORDER_SERVICE, UPLOAD_SERVICE } from "@/services/api.service";
import { useSearchParams } from "next/navigation";

const productData: Product[] = [
	{
		image: "/images/product/product-02.png",
		name: "Apple Watch Series 7",
		category: "Electronics",
		price: 296,
		quantity: 296,
		sold: 22,
		profit: 45,
	},
	{
		image: "/images/product/product-02.png",
		name: "Macbook Pro M1",
		category: "Electronics",
		quantity: 296,
		price: 546,
		sold: 12,
		profit: 125,
	},
	{
		image: "/images/product/product-03.png",
		name: "Dell Inspiron 15",
		category: "Electronics",
		quantity: 296,
		price: 443,
		sold: 64,
		profit: 247,
	},
	{
		image: "/images/product/product-04.png",
		name: "HP Probook 450",
		category: "Electronics",
		quantity: 296,
		price: 499,
		sold: 72,
		profit: 103,
	},
];
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
}

const OrderForm: React.FC = () => {

	const [file, setFile] = useState(null);
	const [data, setData] = useState({
		total_price: '',
		total_discount: '',
		discount: 0,
		status: '',
	});
	const [transaction, setTransaction]: any = useState(null);


	const [title, setTitle] = useState('Tạo mới');
	const [id, setId] = useState(0);
	const params = useSearchParams();

	useEffect(() => {
		let id = Number(params.get('id') || 0);
		setTransaction([
			{
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
		])
		setId(id);
		if (id) {
			setTitle('Cập nhật');
			getData(id)
		}
	}, [params.get('id')]);

	const getData = async (id: any) => {
		const response: any = await ORDER_SERVICE.show(id);
		if (response?.status == 'success') {
			console.log(response);
			setData(response.data || null);
			let trans = response.data.transactions || [INIT_TRAN];
			setTransaction(trans)
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
		let body 
		if(id) {

		}
	}

	const genTotalPrice: any = (index: any) => {
		return Number(transaction[index].quantity) * Number(transaction[index].price)
	}


	return (
		<DefaultLayout>
			<Breadcrumb pageName={title} subName="Order" />
			<div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
				<div className="border-b border-stroke px-6.5 py-4 dark:border-strokedark">
					<h3 className="font-medium text-black dark:text-white text-xl">
						{title}
					</h3>
				</div>
				<div className="flex flex-col gap-5.5 p-6.5">
					<form>
						{!id && <div className="mb-5">
							<label className="mb-3 block text-sm font-medium text-black dark:text-white">
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
											placeholder="Active Input"
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
											<b>{genTotalPrice(key)} đ</b>
										</p>
									</div>
								</div>
							))}

							<div className="inline-flex items-center justify-center 
							rounded-md bg-success px-20 py-2 text-center 
							font-medium text-white hover:bg-opacity-90 lg:px-8 xl:px-10"
								onClick={(e) => {
									transaction.push(INIT_TRAN)
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
									<p className="font-medium text-center text-xl">123123</p>
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
