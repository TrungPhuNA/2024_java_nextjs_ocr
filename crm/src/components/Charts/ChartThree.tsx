import { ApexOptions } from "apexcharts";
import React, { useEffect, useState } from "react";
import ReactApexChart from "react-apexcharts";

interface ChartThreeState {
	series: number[];
}

const options: ApexOptions = {
	chart: {
		fontFamily: "Satoshi, sans-serif",
		type: "donut",
	},
	colors: ["#FFCC33", "#33CC33"],
	labels: ["Desktop", "Tablet"],
	legend: {
		show: false,
		position: "bottom",
	},

	plotOptions: {
		pie: {
			donut: {
				size: "65%",
				background: "transparent",
			},
		},
	},
	dataLabels: {
		enabled: false,
	},
	responsive: [
		{
			breakpoint: 2600,
			options: {
				chart: {
					width: 380,
				},
			},
		},
		{
			breakpoint: 640,
			options: {
				chart: {
					width: 200,
				},
			},
		},
	],
};

const ChartThree: React.FC = (props: any) => {
	const [state, setState] = useState<ChartThreeState>({
		series: [65, 34, 12, 56],
	});
	const [optionChart, setOptionChart] = useState({ ...options });


	useEffect(() => {
		if (props.loading) {
			if (props.data?.length > 0) {
				let stateData: ChartThreeState = { ...state };
				let data = props.data.filter((item: any) => item.status == 1 || item.status == 2)
				stateData.series = data?.map((item: any) => {
					if (item.status == 1) return item.total;
					return item.total
				});
				let optionData: ApexOptions = { ...optionChart };
				optionData.labels = data?.map((item: any) => {
					if (item.status == 1) return 'Chưa thanh toán';
					return 'Đã thanh toán';
				})
				setOptionChart(optionData);
				console.log(optionData);

				setState(stateData)
			}
			props.setLoading(false)
		}
	}, [props.loading])

	return (
		<div className="col-span-12 rounded-sm border border-stroke bg-white px-5 pb-5 pt-7.5 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:col-span-5">
			<div className="mb-3 justify-between gap-4 sm:flex">
				<div>
					<h5 className="text-xl font-semibold text-black dark:text-white">
						Status Order
					</h5>
				</div>

			</div>

			<div className="mb-2 flex">
				<div id="chartThree" className="mx-auto w-[50%] flex justify-center">
					<ReactApexChart
						options={optionChart}
						series={state.series}
						type="donut"
					/>
				</div>
				<div className="-mx-8 w-[25%] items-center justify-center gap-y-3">
					{optionChart.labels?.length > 0 && optionChart.labels?.map((item: any, index: any) => {
						return <div className="w-full px-8" key={index}>
							<div className="flex w-full items-center">
								<span className={`mr-2 block h-3 w-full max-w-3 rounded-full ${index == 0 && 'bg-[#FFCC33]' || 'bg-[#33CC33]' }`}></span>
								<div className=" w-full text-sm font-medium text-black dark:text-white">
									<p className="font-weight text-nowrap"><b>{item}</b></p>
									<span> {state.series[index]} Đơn hàng </span>
								</div>

							</div>
						</div>
					})}
				</div>
			</div>


		</div>
	);
};

export default ChartThree;
