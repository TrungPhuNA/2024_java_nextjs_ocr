"use client";
import React, { useEffect, useState } from "react";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";

import DefaultLayout from "@/components/Layouts/DefaultLayout";

import { Product } from "@/types/product";
import Link from "next/link";
import { CATEGORY_SERVICE, COMMON_API, ORDER_SERVICE, UPLOAD_SERVICE } from "@/services/api.service";
import { redirect, useRouter, useSearchParams } from "next/navigation";
import { checkTextOrder, formatMoney, getItem, setField } from "@/services/helpers.service";
import SelectGroupTwo from "@/components/SelectGroup/SelectGroupTwo";
import Loader from "@/components/common/Loader";
import CkeditorPage from "@/components/common/CkEditor";



const INIT_FORM: any = {
	name: '',
	description: '',
	status: "",
};



const RoomForm: React.FC = () => {

	const [file, setFile] = useState(null);
	const [loading, setLoading] = useState(false);
	const [data, setData]: any = useState({...INIT_FORM});


	const [title, setTitle] = useState('Tạo mới');
	const [id, setId] = useState(0);
	const params = useSearchParams();
	const [errorFile, setErrorFile] = useState('');
	const user = getItem('user');
	const router = useRouter();
	const [error, setError] = useState({
		name: '',
		status: '',
	});


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
		const response: any = await COMMON_API.show('room', id);
		setLoading(false);

		if (response?.status == "success") { 
			setData( {
				name: response?.data?.name,
				status: response?.data?.status ?  response?.data?.status  + "" : "",
				description: response?.data?.description,
				user_id: response?.data?.user?.id || user?.id,
			})
		}

	}



	const submit = async (e: any) => {
		e.preventDefault();
		let response: any;
		let bodyData: any = data;

		let count = 0;
		let objError = {
			name: '',
			status: ''
		}
		if (!bodyData.name || bodyData.name == '') {
			objError.name = 'Tên phòng không được để trống.'
			count++;
		}

		if (!bodyData.status || bodyData.status == '') {
			objError.status = 'Trạng thái không được để trống.'
			count++;
		}


		if (count > 0) {
			setError(objError);
			return;
		}

		setLoading(true);

		if (id) {
			response = await COMMON_API.update('room', id, bodyData);
		} else {
			bodyData.user_id = user?.id;
			response = await COMMON_API.store('room', bodyData);
		}
		setLoading(false);

		if (response?.status == 'success') {
			resetForm()
			router.push('/room');
		}
	}

	const resetForm = () => {
		setData({ ...INIT_FORM });
	}


	return (
		<DefaultLayout>
			<Breadcrumb pageName={title} subName="Phòng ban" />
			<div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
				<div className="border-b border-stroke px-6.5 py-4 dark:border-strokedark">
					<h3 className="font-medium text-black dark:text-white text-2xl">
						{title}
					</h3>
				</div>
				{loading && <Loader className={"bg-opacity-60 bg-white z-50 fixed top-0 left-0 w-full h-full"} />}

				<div className="flex flex-col gap-5.5 p-6.5">
					<form>
						<div className="md:grid md:grid-cols-2 md:gap-4">
							<div className="mb-5">
								<label className="mb-3 text-xl block text-sm font-medium text-black dark:text-white">
									Tên phòng ban
								</label>
								<input
									type="text"
									placeholder="Tên phòng ban"
									defaultValue={data.name}
									onChange={e => {
										setField(e?.target?.value, 'name', data, setData);
									}}
									className={`w-full	 rounded-lg border-[1.5px] ${error.name != '' ? ' border-red ' : ''} border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white`}
								/>
								{error.name != '' && <span className="text-red text-xl mt-3">{error.name}</span>}

							</div>
							<div className="mb-5">
								<SelectGroupTwo
									title={'Trạng thái'}
									options={[
										{
											id: "ACTIVE",
											name: 'Đang hoạt động'
										},
										{
											id: "INACTIVE",
											name: 'Ngưng hoạt động'
										}
									]}
									key_obj={'status'}
									value={data.status}
									form={data}
									setForm={setData}
								/>

							</div>
						</div>
						<CkeditorPage
							title={'Chi tiết phòng ban'}
							key_obj={'description'}
							value={data.description}
							form={data}
							setForm={setData}
						/>
						<div className="flex justify-center mt-5">
							<Link href={'/order'} className="inline-flex items-center justify-center 
							rounded-md bg-gray mr-3 px-5 py-2 text-center 
							font-medium hover:bg-gray-900 lg:px-8 xl:px-10">
								Cancel
							</Link>
							<button className="inline-flex items-center justify-center
							rounded-md bg-primary px-5 py-2 text-center
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

export default RoomForm;
