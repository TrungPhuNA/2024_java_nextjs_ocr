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
	salary: '',
	user_id: '',
	updated_by: "",
	workday: "",
	allowance: "",
	receive_salary: "",
	from_date: "",
	to_date: "",
	status: ""

};



const SalaryForm: React.FC = () => {

	const [file, setFile] = useState(null);

	const [imgBase64, setImgBase64]: any = useState(null);
	let refFile = useRef(null);
	const user = getItem('user');

	const [loading, setLoading] = useState(false);

	const [data, setData]: any = useState({ ...INIT_FORM, updated_by: user?.id, updated_by_name: user?.name });

	const [title, setTitle] = useState('Tạo mới');

	const [users, setUsers] = useState([]);

	const [id, setId] = useState(0);
	const params = useSearchParams();
	const [errorFile, setErrorFile] = useState('');
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
		const response: any = await COMMON_API.show('salary', id);
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
		if (!bodyData.salary || bodyData.salary == '') {
			objError.salary = 'Tiền lương không được để trống.'
			count++;
		}

		if (!bodyData.status || bodyData.status?.trim() == '') {
			objError.status = 'Trạng thái không được để trống.'
			count++;
		}

		if (!bodyData.receive_salary || bodyData.receive_salary?.toString()?.trim() == '') {
			objError.receive_salary = 'Lương thực nhận không được để trống.'
			count++;
		}

		if (!bodyData.workday || bodyData.workday?.toString()?.trim() == '') {
			objError.workday = 'Ngày công không được để trống.'
			count++;
		}

		if ((!bodyData.from_date || bodyData.from_date?.trim() == '') || (!bodyData.to_date || bodyData.to_date?.trim() == '')) {
			objError.from_date = 'Thời gian không được để trống.'
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
			response = await COMMON_API.update('salary', id, bodyData);
		} else {
			response = await COMMON_API.store('salary', bodyData);
		}
		setLoading(false);

		if (response?.status == 'success') {
			resetForm()
			router.push('/salary');
		}
	}

	const resetForm = () => {
		setData({ ...INIT_FORM });
	}

	useEffect(() => {
		if(data.salary && data?.salary.toString()?.trim() != "" && data.workday && data.workday?.toString().trim() != "") {
			let salaryData = Number(data.salary);
			let workday = Number(data.workday);
			let allowance = Number(data.allowance);
			let totalDayInMonth = moment().daysInMonth();
			let totalSalary = salaryData * (workday/totalDayInMonth) + allowance;
			setData({...data, receive_salary: totalSalary.toFixed(0)})
		}
	}, [data.salary, data.allowance, data.workday])

	useEffect(() => {
		if(data.from_date && data.to_date && data.from_date?.trim() != "" && data.to_date?.trim() != "") {
			if(moment(data.from_date).month() != moment(data.to_date).month()) {
				setError({...error, from_date: 'Vui lòng chọn lại thời gian trong cùng 1 tháng.'})
			} else {
				setError({...error, from_date: ''})
				let workday = subTime(data.from_date, data.to_date);
				if(workday < 0) {
					setError({...error, from_date: 'Vui lòng chọn lại thời gian kỳ trả lương.'})
				} else {
					setData({...data, workday: workday + 1})
				}
			}
			
		}
	}, [ data.from_date, data.to_date,])




	return (
		<DefaultLayout>
			<Breadcrumb pageName={title} subName="Quản lý lương" />
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
							<div className="mb-5">
								<label className="mb-3 text-xl block text-sm font-medium text-black dark:text-white">
									Thời gian
								</label>
								<div className="md:flex">
									<input
										type="date"
										placeholder=""
										defaultValue={data.from_date}
										onChange={e => {
											setField(e?.target?.value, 'from_date', data, setData);
										}}
										className={`w-full	 rounded-lg border-[1.5px] ${error.from_date != '' ? ' border-red ' : ''} border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white`}
									/>
									<input
										type="date"
										placeholder=""
										defaultValue={data.to_date}
										onChange={e => {
											setField(e?.target?.value, 'to_date', data, setData);
										}}
										className={`w-full mt-5 md:mt-0	 rounded-lg border-[1.5px] ${error.from_date != '' ? ' border-red ' : ''} border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white`}
									/>
								</div>
								{error.from_date != '' && <span className="text-red text-xl mt-3">{error.from_date}</span>}

							</div>

							<div className="mb-5">
								<label className="mb-3 text-xl block text-sm font-medium text-black dark:text-white">
									Mức Lương
								</label>
								<input
									type="number"
									placeholder="salary"
									defaultValue={data.salary}
									onChange={e => {
										setField(e?.target?.value, 'salary', data, setData);
									}}
									className={`w-full	 rounded-lg border-[1.5px] ${error.salary != '' ? ' border-red ' : ''} border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white`}
								/>
								{error.salary != '' && <span className="text-red text-xl mt-3">{error.salary}</span>}

							</div>

							<div className="mb-5">
								<label className="mb-3 text-xl block text-sm font-medium text-black dark:text-white">
									Ngày công
								</label>
								<input
									type="number"
									placeholder="workday"
									defaultValue={data.workday}
									onChange={e => {
										setField(e?.target?.value, 'workday', data, setData);
									}}
									className={`w-full	 rounded-lg border-[1.5px] ${error.workday != '' ? ' border-red ' : ''} border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white`}
								/>
								{error.workday != '' && <span className="text-red text-xl mt-3">{error.workday}</span>}
							</div>
							<div className="mb-5">
								<label className="mb-3 text-xl block text-sm font-medium text-black dark:text-white">
									Phụ cấp
								</label>
								<input
									type="number"
									placeholder="Tiền phụ cấp"
									onChange={e => {
										setField(e?.target?.value, 'allowance', data, setData);
									}}
									defaultValue={data.allowance}
									className={`w-full	 rounded-lg border-[1.5px] ${error.allowance != '' ? ' border-red ' : ''} border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white`}
								/>
								{error.allowance != '' && <span className="text-red text-xl mt-3">{error.allowance}</span>}
							</div>
							<div className="mb-5">
								<label className="mb-3 text-xl block text-sm font-medium text-black dark:text-white">
									Lương thực nhận
								</label>
								<input
									type="number"
									placeholder="Lương thực nhận"
									defaultValue={data.receive_salary}
									readOnly={true}
									className={`w-full	 rounded-lg border-[1.5px] ${error.receive_salary != '' ? ' border-red ' : ''} border-primary bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white`}
								/>
								{error.receive_salary != '' && <span className="text-red text-xl mt-3">{error.receive_salary}</span>}
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

export default SalaryForm;
