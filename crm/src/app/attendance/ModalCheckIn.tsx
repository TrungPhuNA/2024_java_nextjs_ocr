import { useEffect, useRef, useState } from "react";
import "antd/dist/reset.css";

import Link from "next/link";
import { Button, Checkbox, DatePicker, Form, message, Modal } from "antd";
import moment from "moment";
import { useForm } from "antd/es/form/Form";
import { validateMessages } from "@/services/constant";
import { ATTENDANCE_SERVICE } from "@/services/api.service";
import { getItem } from "@/services/helpers.service";
import Loader from "@/components/common/Loader";

const ModalCheckInPage = (props: any) => {

	const [loading, setLoading] = useState(false);
	const [user, setUser] = useState(getItem('user'));
	const [form] = useForm();

	const minDate = moment().subtract(1, 'weeks');


	const disablePastDates = (current: any) => {
		return current && current < minDate.startOf('day');
	};

	useEffect(() => {
		if(props.detail) {
			form.setFieldsValue({
				check_in: moment(props.detail.check_in),
				morning: props?.detail?.type?.toLowerCase() == 'all' ||  props?.detail?.type?.toLowerCase() == 'morning',
				afternoon: props?.detail?.type?.toLowerCase() == 'all' ||  props?.detail?.type?.toLowerCase() == 'afternoon',
			})
		}
	}, [props.open])

	const handleCancel = () => {
		form.resetFields();
		props.setOpen(false);
		props.setDetail(null);
		
	}

	const handleOk = async () => {
		let data: any = form.getFieldsValue();
		if (!data?.morning && !data?.afternoon) {
			message.error('Vui long chọn ca làm việc');
			return;
		}
		data.check_in = moment(data.check_in).format('yyyy-MM-DD');
		data.full_name = props?.detail?.full_name ||props?.detail?.user?.name;
		data.email = props?.detail?.email ||props?.detail?.user?.name;
		data.user_id = props?.detail?.user_id ||props?.detail?.user?.id;
		data.type = data?.morning && data?.afternoon ? 'ALL' : (data?.morning && 'MORNING' || 'AFTERNOON')
		setLoading(true);
		const response: any = await ATTENDANCE_SERVICE.update(props.detail.id, data);
		setLoading(false);
		if (response?.status == 'success') {
			message.success('Châm công thanh công!');
			handleCancel();
			props.getDataList({...props.paging})
		} else {
			message.error(response?.message);
		}

	}

	return (
		<Modal
			open={props.open}
			centered={true}
			title="Chấm công"
			onCancel={handleCancel}
			footer={false}
		>
			{loading && <Loader className={"bg-opacity-60 bg-white z-50 fixed top-0 left-0 w-full h-full"} />}

			<Form
				labelCol={{ span: 4 }}
				wrapperCol={{ span: 14 }}
				form={form}
				onFinish={handleOk}
				validateMessages={validateMessages}
				layout="horizontal"
				style={{ maxWidth: 500 }}
			>
				<Form.Item label="Thời gian" name={'check_in'}
					rules={[{ required: true, message: 'Thời gian không được để trống' }]}>
					<DatePicker disabledDate={disablePastDates} format={'DD-MM-YYYY'} placeholder="Chọn thời gian" className="w-full" />
				</Form.Item>
				<div className="flex gap-4 items-center">
					<label >Ca làm việc</label>
					<Form.Item name="morning" className="mb-0" valuePropName="checked">
						<Checkbox className="text-nowrap">Ca sáng</Checkbox>
					</Form.Item>
					<Form.Item name="afternoon" className="mb-0" valuePropName="checked">
						<Checkbox className="text-nowrap" >Ca chiều</Checkbox>
					</Form.Item>
				</div>
				<div className="flex gap-4 justify-end">
					<Button key="back" onClick={handleCancel}>
						Hủy
					</Button>
					<button type="submit" className="btn px-6 bg-primary rounded-md text-white hover:bg-opacity-90">
						Xác nhận
					</button>

				</div>
			</Form>
		</Modal>
	);
};

export default ModalCheckInPage;
