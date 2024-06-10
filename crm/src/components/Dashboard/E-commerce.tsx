"use client";
import React, { useEffect, useState } from "react";
import ChartOne from "../Charts/ChartOne";
import ChartThree from "../Charts/ChartThree";
import ChartTwo from "../Charts/ChartTwo";
import ChatCard from "../Chat/ChatCard";
import TableOne from "../Tables/TableOne";
import CardDataStats from "../CardDataStats";
import MapOne from "../Maps/MapOne";
import { COMMON_API, ORDER_SERVICE } from "@/services/api.service";
import { formatMoney, getItem } from "@/services/helpers.service";
import moment from "moment";
import Loader from "../common/Loader";
import { AiFillHome, AiFillMoneyCollect, AiFillStar, AiOutlineCluster, AiOutlineSchedule, AiOutlineShopping, AiOutlineUser } from "react-icons/ai";

const ECommerce: React.FC = () => {
	const [data, setData]: any = useState({
		total_user: 0,
		user_ranks: [],
		user_rooms: [],
		total_room: 0,
		total_rank: 0,
		data_bonus: {
			bonus: null,
			discipline: null
		}
	});
	const [params, setParams]: any = useState({
		month: null,
	});

	const [view, setView] = useState(getItem('view'));


	const [loading, setLoading] = useState(false);
	const [change, setChange] = useState(false);

	useEffect(() => {
		if (params.month) {
			setData({ ...data })
		}
		getDataList()
	}, [params.month]);

	const getDataList = async () => {
		setLoading(true);
		setChange(true)
		if (!params?.month) {
			setParams({ month: moment().month() + 1 })
		}
		let response: any = null;
		if (view == "ORDER") {
			response = await ORDER_SERVICE.statistic(params);
		} else {
			response = await COMMON_API.getList('statistic', params);
		}
		setLoading(false);
		if (response?.status == 'success') {
			if (view == "ORDER") {
				setData({ ...response.data } || null);
			} else {
				let data_bonus = {
					bonus: response?.data?.data_bonus?.find((item: any) => item.type == "BONUS"),
					discipline: response?.data?.data_bonus?.find((item: any) => item.type == "DISCIPLINE")
				}
				setData({ ...response.data, data_bonus: data_bonus } || null);
			}
		}

	}
	return (
		<>
			{loading && <Loader className={"bg-opacity-60 bg-white z-50 fixed top-0 left-0 w-full h-full"} />}

			<div className="grid grid-cols-1 gap-4 md:grid-cols-3 md:gap-6 2xl:gap-7.5">
				{view == "ORDER" ? <>
					<CardDataStats title="Đơn hàng " total={(data?.total_order || 0) + ""}>
						<AiOutlineShopping />
					</CardDataStats>
					<CardDataStats title="Danh mục" total={(data?.total_category || 0) + ""}>
						<AiOutlineCluster />
					</CardDataStats>
					<CardDataStats title="Doanh số " total={formatMoney(data?.total_price || 0)}>
						<AiFillMoneyCollect />
					</CardDataStats>

				</> : <>
					<CardDataStats title="Nhân viên " total={(data?.total_user || 0) + ""}>
						<AiOutlineUser />
					</CardDataStats>
					<CardDataStats title="Chức vụ " total={(data?.total_rank || 0) + ""}>
						<AiOutlineCluster />
					</CardDataStats>
					<CardDataStats title="Phòng ban " total={(data?.total_room || 0) + ""}>
						<AiFillHome />
					</CardDataStats>
					<CardDataStats title="Thưởng" total={(data?.data_bonus?.bonus?.total || 0) + ""}>
						<AiFillStar className="text-warning text-xl" />
					</CardDataStats>
					<CardDataStats title="Kỷ luật" total={(data?.data_bonus?.discipline?.total || 0) + ""}>
						<AiOutlineSchedule className="text-red text-xl" />
					</CardDataStats>
				</>}
			</div>

			{view == "ORDER" ? <>
				<div className="mt-4 gap-4 md:mt-6 md:gap-6 2xl:mt-7.5 2xl:gap-7.5">
					{(data?.group_by_day?.length > 0 && !loading) && <ChartOne
						data={data?.group_by_day} setParams={setParams}
						loading={change}
						setLoading={setChange}
						params={params} />}
					{data?.group_by_status && !loading && <ChartThree
						data={data?.group_by_status} setParams={setParams}
						loading={change}
						setLoading={setChange}
						params={params} />}
				</div>
			</> : <>

				<div className="md:grid md:grid-cols-2 md:gap-6 mt-5 bg-white">
					<div className="p-4">
						<h2 className="text-2xl">Chức vụ</h2>
						<div className="max-w-full overflow-x-auto border">
							<table className="w-full table-auto">
								<thead>
									<tr className="bg-gray-2 text-left dark:bg-meta-4">
										<th className="py-4 px-4 font-medium text-black dark:text-white xl:pl-11">
											Chức vụ
										</th>
										<th className=" py-4 px-4 font-medium text-nowrap
									text-black dark:text-white xl:pl-11">
											Số lượng nhân viên
										</th>
									</tr>
								</thead>
								<tbody>
									{data?.user_ranks?.length > 0 ? data?.user_ranks.map((item: any, key: any) => (
										<tr key={key}>
											<td className="border-b border-[#eee] py-5 px-4 pl-9 dark:border-strokedark">
												{item.name}
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												{item.total}
											</td>
										</tr>
									)) :

										<tr>
											<td colSpan={2} className="text-center mt-2">
												<p className="mt-5">Không có dữ liệu</p>
											</td>
										</tr>
									}


								</tbody>
							</table>
						</div>
					</div>
					<div className="p-4">
						<h2 className="text-2xl">Phòng ban</h2>
						<div className="max-w-full overflow-x-auto border">
							<table className="w-full table-auto">
								<thead>
									<tr className="bg-gray-2 text-left dark:bg-meta-4">
										<th className="py-4 px-4 font-medium text-black dark:text-white xl:pl-11">
											Phòng ban
										</th>
										<th className=" py-4 px-4 font-medium text-nowrap
									text-black dark:text-white xl:pl-11">
											Số lượng nhân viên
										</th>
									</tr>
								</thead>
								<tbody>
									{data?.user_rooms?.length > 0 ? data?.user_rooms.map((item: any, key: any) => (
										<tr key={key}>
											<td className="border-b border-[#eee] py-5 px-4 pl-9 dark:border-strokedark">
												{item.name}
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												{item.total}
											</td>
										</tr>
									)) :

										<tr>
											<td colSpan={2} className="text-center mt-2">
												<p className="mt-5">Không có dữ liệu</p>
											</td>
										</tr>
									}


								</tbody>
							</table>
						</div>
					</div>

				</div>
			</>}


		</>
	);
};

export default ECommerce;
