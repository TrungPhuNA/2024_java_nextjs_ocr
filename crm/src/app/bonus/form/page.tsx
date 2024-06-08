"use client";
import React, { useEffect, useRef, useState } from "react";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";

import DefaultLayout from "@/components/Layouts/DefaultLayout";

import { Product } from "@/types/product";
import Link from "next/link";
import { CATEGORY_SERVICE, COMMON_API, ORDER_SERVICE, UPLOAD_SERVICE } from "@/services/api.service";
import { redirect, useRouter, useSearchParams } from "next/navigation";
import { buildImage, checkTextOrder, formatMoney, formatTime, getItem, readFile, setField, subTime } from "@/services/helpers.service";
import SelectGroupTwo from "@/components/SelectGroup/SelectGroupTwo";
import Loader from "@/components/common/Loader";
import CkeditorPage from "@/components/common/CkEditor";
import '../../../assets/style.css'
import moment from "moment";


const INIT_FORM: any = {
	name: '',
	user_id: '',
	updated_by: "",
	content: "",
	type: "",
	data_value: "",
	status: "",
	updated_by_name: ""
};



const BonusForm: React.FC = () => {


	const user = getItem('user');

	const [loading, setLoading] = useState(false);

	const [data, setData]: any = useState({ ...INIT_FORM, updated_by: user?.id, updated_by_name: user?.name });

	const [title, setTitle] = useState('Tạo mới');

	const [users, setUsers] = useState([]);
	const [id, setId] = useState(0);
	const params = useSearchParams();
	const router = useRouter();
	const [error, setError] = useState({
		...INIT_FORM
	});


	useEffect(() => {
		let id = Number(params.get('id') || 0);

		setId(id);
		if (id) {
			setTitle('Cập nhật');
			getData(id)
		} else {
			setData({ ...INIT_FORM, updated_by: user?.id, updated_by_name: user?.name })

		}
		getUsersList();


	}, [params.get('id')]);


	const getData = async (id: any) => {
		setLoading(true);
		const response: any = await COMMON_API.show('bonus', id);
		setLoading(false);

		if (response?.status == "success") {
			setData({
				salary: response?.data?.salary,
				user_id: response?.data?.user?.id,
				updated_by: response?.data?.updated_by?.id || user?.id,
				updated_by_name: response?.data?.updated_by?.name,
				workday: response?.data?.workday,
				allowance: response?.data?.allowance,
				receive_salary: response?.data?.receive_salary,
				from_date: formatTime(response?.data?.from_date, 'yyyy-MM-DD'),
				to_date: formatTime(response?.data?.to_date, 'yyyy-MM-DD'),
				status: response?.data?.status

			});
		}

	}

	const getUsersList = async () => {
		const response: any = await COMMON_API.getList('user', { page: 1, page_size: 1000, user_type: 'USER' });
		if (response?.status == "success") {
			setUsers(response?.data)
		}

	}



	const submit = async (e: any) => {
		e.preventDefault();
		let response: any;
		let bodyData: any = data;

		let count = 0;
		let objError = {
			...INIT_FORM
		}
		if (!bodyData.name || bodyData.name == '') {
			objError.name = 'Tiêu đề không được để trống.'
			count++;
		}

		if (!bodyData.status || bodyData.status?.trim() == '') {
			objError.status = 'Trạng thái không được để trống.'
			count++;
		}

		if (!bodyData.type || bodyData.type?.toString()?.trim() == '') {
			objError.type = 'Phân loại không được để trống.'
			count++;
		}

		if (!bodyData.user_id || bodyData.user_id?.toString()?.trim() == '') {
			objError.user_id = 'Nhân viên không được để trống.'
			count++;
		}


		if (count > 0) {
			setError(objError);
			return;
		}


		setLoading(true);

		if (id) {
			response = await COMMON_API.update('bonus', id, bodyData);
		} else {
			response = await COMMON_API.store('bonus', bodyData);
		}
		setLoading(false);

		if (response?.status == 'success') {
			resetForm()
			router.push('/bonus');
		}
	}

	const resetForm = () => {
		setData({ ...INIT_FORM });
	}




	return (
		<DefaultLayout>
			<Breadcrumb pageName={title} subName="Quản lý thưởng - kỷ luật" />
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
									Tiêu đề
								</label>
								<input
									type="text"
									placeholder="Tiêu đề"
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
									title={'Nhân viên'}
									options={users}
									key_obj={'user_id'}
									value={data.user_id}
									form={data}
									setForm={setData}
								/>
								{error.user_id != '' && <span className="text-red text-xl mt-3">{error.user_id}</span>}
							</div>
						</div>


						<CkeditorPage
							title={'Nội dung'}
							key_obj={'content'}
							value={data.content}
							form={data}
							setForm={setData}
						/>
						<div className="md:grid md:grid-cols-2 md:gap-4 mt-5">

							<div className="mb-5">
								<SelectGroupTwo
									title={'Phân loại'}
									options={[
										{
											id: "BONUS",
											name: 'Thưởng'
										},
										{
											id: "DISCIPLINE",
											name: 'Kỷ luật'
										}
									]}
									key_obj={'type'}
									value={data.type}
									form={data}
									setForm={setData}
								/>
								{error.type != '' && <span className="text-red text-xl mt-3">{error.type}</span>}
							</div>

							<div className="mb-5">
								<label className="mb-3 text-xl block text-sm font-medium text-black dark:text-white">
									Giá trị
								</label>
								<input
									type="number"
									placeholder="data_value"
									defaultValue={data.data_value}
									onChange={e => {
										setField(e?.target?.value, 'data_value', data, setData);
									}}
									className={`w-full	 rounded-lg border-[1.5px] ${error.data_value != '' ? ' border-red ' : ''} border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white`}
								/>
								{error.data_value != '' && <span className="text-red text-xl mt-3">{error.data_value}</span>}

							</div>

							<div className="mb-5">
								<SelectGroupTwo
									title={'Trạng thái'}
									options={[
										{
											id: "PENDING",
											name: 'Chờ duyệt'
										},
										{
											id: "APPROVED",
											name: 'Đã duyệt'
										}
									]}
									key_obj={'status'}
									value={data.status}
									form={data}
									setForm={setData}
								/>
								{error.status != '' && <span className="text-red text-xl mt-3">{error.status}</span>}
							</div>

							<div className="mb-5">
								<label className="mb-3 text-xl block text-sm font-medium text-black dark:text-white">
									Người tạo
								</label>
								<input
									type="text"
									placeholder="Người tạo"
									defaultValue={data.updated_by_name}
									readOnly
									className={`w-full	 rounded-lg border-[1.5px]  border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white`}
								/>
								{error.updated_by_name != '' && <span className="text-red text-xl mt-3">{error.updated_by_name}</span>}
							</div>
						</div>
						<div className="flex justify-center mt-5">
							<Link href={'/salary'} className="inline-flex items-center justify-center 
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

export default BonusForm;
