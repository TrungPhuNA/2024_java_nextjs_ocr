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



const CategoryForm: React.FC = () => {

	const [file, setFile] = useState(null);
	const [data, setData]: any = useState({
		name: '',
		status: '',
	});

	const [title, setTitle] = useState('Tạo mới');
	const [id, setId] = useState(0);
	const [loading, setLoading] = useState(false);
	const params = useSearchParams();

	const router = useRouter()


	useEffect(() => {
		let id = Number(params.get('id') || 0);

		setId(id);
		if (id) {
			setTitle('Cập nhật');
			getData(id)
		}
	}, [params.get('id')]);

	const getData = async (id: any) => {
		setLoading(true);
		const response: any = await CATEGORY_SERVICE.show(id);
		setLoading(false);

		if (response?.status == 'success') {
			setData(response.data || null);
		}
	}

	const submit = async (e: any) => {
		e.preventDefault();
		let response: any;
		let bodyData: any = data;
		setLoading(true);
		
		if (id) {
			response =  await CATEGORY_SERVICE.update(id, bodyData);
		} else {
			response =  await CATEGORY_SERVICE.store(bodyData);
		}
		setLoading(false);

		if(response?.status == 'success') {
			resetForm();
			router.push('/category');
		}
	}

	const resetForm = () => {
		setData({
			name: '',
			status: '',
		});
	}


	return (
		<DefaultLayout>
			<Breadcrumb pageName={title} subName="Category" />
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
									Tên phân loại
								</label>
								<input
									type="text"
									placeholder="Tên phân loại"
									defaultValue={data.name}
									onChange={e => {
										setField(e?.target?.value, 'name', data, setData);
									}}
									className="w-full rounded-lg border-[1.5px] border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white"
								/>
							</div>
							<div className="mb-5">
								<SelectGroupTwo
									title={'Trạng thái'}
									options={[
										{
											id: -1,
											name: 'Inactive'
										},
										{
											id: 1,
											name: 'Active'
										}
									]}
									key_obj={'status'}
									value={data.status}
									form={data}
									setForm={setData}
								/>
							</div>
						</div>
						<div className="flex justify-center">
							<Link href={'/category'} className="inline-flex items-center justify-center rounded-md bg-gray mr-3 px-10 py-4 text-center font-medium hover:bg-gray-900 lg:px-8 xl:px-10">Cancel</Link>
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

export default CategoryForm;
