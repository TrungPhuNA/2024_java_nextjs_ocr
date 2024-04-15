import { ApexOptions } from "apexcharts";
import React, { useEffect, useState } from "react";
import ReactApexChart from "react-apexcharts";
import SelectGroupTwo from "../SelectGroup/SelectGroupTwo";
import moment from "moment";
import { formatTime } from "@/services/helpers.service";

const options: ApexOptions = {
	legend: {
		show: false,
		position: "top",
		horizontalAlign: "left",
	},
	colors: ["#3C50E0", "#80CAEE"],
	chart: {
		fontFamily: "Satoshi, sans-serif",
		height: 335,
		type: "area",
		dropShadow: {
			enabled: true,
			color: "#623CEA14",
			top: 10,
			blur: 4,
			left: 0,
			opacity: 0.1,
		},

		toolbar: {
			show: false,
		},
	},
	responsive: [
		{
			breakpoint: 1024,
			options: {
				chart: {
					height: 300,
				},
			},
		},
		{
			breakpoint: 1366,
			options: {
				chart: {
					height: 350,
				},
			},
		},
	],
	stroke: {
		width: [2, 2],
		curve: "straight",
	},
	// labels: {
	//   show: false,
	//   position: "top",
	// },
	grid: {
		xaxis: {
			lines: {
				show: true,
			},
		},
		yaxis: {
			lines: {
				show: true,
			},
		},
	},
	dataLabels: {
		enabled: false,
	},
	markers: {
		size: 4,
		colors: "#fff",
		strokeColors: ["#3056D3", "#80CAEE"],
		strokeWidth: 3,
		strokeOpacity: 0.9,
		strokeDashArray: 0,
		fillOpacity: 1,
		discrete: [],
		hover: {
			size: undefined,
			sizeOffset: 5,
		},
	},
	xaxis: {
		type: "category",
		categories: [],
		axisBorder: {
			show: false,
		},
		axisTicks: {
			show: false,
		},
	},
	yaxis: {
		title: {
			style: {
				fontSize: "0px",
			},
		},
		min: 0,
		max: 100,
	},
};

interface ChartOneState {
	series: {
		name: string;
		data: number[];
	}[];
}

const ChartOne: React.FC = (props: any) => {
	const [month, setMonth] = useState([]);
	const [optionChart, setOptionChart] = useState({ ...options });

	const [state, setState] = useState<ChartOneState>({
		series: [
			{
				name: "Product One",
				data: [],
			},
		],
	});

	const handleReset = () => {
		setState((prevState) => ({
			...prevState,
		}));
	};
	handleReset;

	useEffect(() => {
		let monthData = [...Array(12)].reduce((newData, item, index) => {
			let obj = {
				id: index + 1,
				name: 'Jan'
			};
			if (index == 1) obj.name = 'Feb';
			if (index == 2) obj.name = 'Mar';
			if (index == 3) obj.name = 'Apr';
			if (index == 4) obj.name = 'May';
			if (index == 5) obj.name = 'Jun';
			if (index == 6) obj.name = 'July';
			if (index == 7) obj.name = 'Aug';
			if (index == 8) obj.name = 'Sep';
			if (index == 9) obj.name = 'Oct';
			if (index == 10) obj.name = 'Nov';
			if (index == 11) obj.name = 'Dec';
			newData.push(obj)
			return newData
		}, [])
		setMonth(monthData)
	}, []);

	useEffect(() => {
		if (props.loading) {
			let optionsData: ApexOptions = { ...optionChart };
			console.log(props.data);
			if (props.data?.length > 0) {
				optionsData.xaxis.categories = props.data?.map((item: any) => formatTime(item.date, 'DD/MM/yyyy'));
				let stateData: ChartOneState = { ...state };
				stateData.series[0].data = props.data?.map((item: any) => item.price);
				let maxValue = Math.max(...stateData.series[0].data);

				optionsData.yaxis.max = maxValue
				stateData.series[0].name = "Total price order";
				setOptionChart(optionsData);

				setState(stateData)
			}
			props.setLoading(false)
		}
	}, [props.loading])


	return (
		<div className="col-span-12 rounded-sm border border-stroke bg-white px-5 pb-5 pt-7.5 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:col-span-8">
			<div className="flex flex-wrap items-start justify-between gap-3 sm:flex-nowrap">
				<div className="flex w-full flex-wrap gap-3 sm:gap-5">
					<div className="flex min-w-47.5">
						<span className="mr-2 mt-1 flex h-4 w-full max-w-4 items-center justify-center rounded-full border border-primary">
							<span className="block h-2.5 w-full max-w-2.5 rounded-full bg-primary"></span>
						</span>
						<div className="w-full">
							<p className="font-semibold text-primary">Total price</p>
							{props.data?.length > 0 && <p className="text-sm font-medium text-nowrap">{formatTime(props.data[0].date, 'DD/MM/yyyy')} - {formatTime(props.data[props.data.length - 1].date, 'DD/MM/yyyy')}</p>}
						</div>
					</div>

				</div>
				<div className="w-[150px]">
					<div className="relative z-20 inline-block">
						<SelectGroupTwo
							options={month}
							key_obj={'month'}
							value={props.params.month}
							form={props.params}
							setForm={props.setParams}
						/>
					</div>
				</div>
			</div>

			<div>
				<div id="chartOne" className="-ml-5">
					<ReactApexChart
						options={optionChart}
						series={state.series}
						type="area"
						height={350}
						width={"100%"}
					/>
				</div>
			</div>
		</div>
	);
};

export default ChartOne;
