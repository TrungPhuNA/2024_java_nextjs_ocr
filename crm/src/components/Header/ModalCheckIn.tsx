import { useEffect, useRef, useState } from "react";
import "antd/dist/reset.css";

import Link from "next/link";
import { Button, Checkbox, DatePicker, Form, message, Modal } from "antd";
import moment from "moment";
import { useForm } from "antd/es/form/Form";
import { validateMessages } from "@/services/constant";
import Loader from "../common/Loader";
import { ATTENDANCE_SERVICE } from "@/services/api.service";
import { getItem } from "@/services/helpers.service";

const ModalCheckIn = () => {

	const [open, setOpen] = useState(false);
	const [loading, setLoading] = useState(false);
	const [user, setUser] = useState(getItem('user'));
	const [form] = useForm();

	const minDate = moment().subtract(1, 'weeks');


	const disablePastDates = (current: any) => {
		// Kiểm tra nếu ngày hiện tại nhỏ hơn ngày tối thiểu
		return current && current < minDate.startOf('day');
	};

	const handleCancel = () => {
		form.resetFields();
		setOpen(false);
	}

	const handleOk = async () => {
		let data: any = form.getFieldsValue();
		if (!data?.morning && !data?.afternoon) {
			message.error('Vui long chọn ca làm việc');
			return;
		}
		data.check_in = moment(data.check_in).format('yyyy-MM-DD');
		data.full_name = user?.name;
		data.email = user?.email;
		data.user_id = user?.id;
		data.type = data?.morning && data?.afternoon ? 'ALL' : (data?.morning && 'MORNING' || 'AFTERNOON')
		console.log(data);
		setLoading(true);
		const response: any = await ATTENDANCE_SERVICE.store(data);
		setLoading(false);
		if(response?.status == 'success') {
			message.success('Châm công thanh công!');
			form.resetFields();
			setOpen(false);
			if(window.location.pathname?.includes('attendance')) {
				window.location.reload();
			}
		} else {
			message.error(response?.message);
		}

	}

	const showModal = () => {
		setOpen(true);
	};

	return (
		<li className="relative">
			<Button type="primary" onClick={showModal}>
				Chấm công
			</Button>

			<Modal
				open={open}
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
		</li>
	);
};

export default ModalCheckIn;
