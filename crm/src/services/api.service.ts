import axios from "axios";
import { deleteMethod, getMethod, postMethod, putMethod, uploadFile } from "./apiService.service"
import { URL_API, WEB_VALUE } from "./constant";
import { buildFilter } from "./helpers.service";

export const ORDER_SERVICE = {
	async getList(filters: any) {
		const params = buildFilter(filters);
		return await getMethod(`${URL_API.ORDER}/list`, params);
	},
	async store(data: any) {
		return await postMethod(`${URL_API.ORDER}/store`, data);
	},
	async show(id: any) {
		return await getMethod(`${URL_API.ORDER}/show/` + id, {});
	},
	async update(id: any, data: any) {
		return await putMethod(`${URL_API.ORDER}/update/` + id, data);
	},
	async delete(id: any) {
		return await deleteMethod(`${URL_API.ORDER}/delete/` + id);
	},
};

export const AUTH_SERVICE = {
	async register(data: any) {
		return await postMethod(`${URL_API.AUTH}/register`, data);
	},
	async store(data: any) {
		return await postMethod(`${URL_API.AUTH}/store`, data);
	},
	async show() {
		return await getMethod(`${URL_API.AUTH}/profile`, {});
	},
	async update(id: any, data: any) {
		return await putMethod(`${URL_API.AUTH}/profile/update` + id, data);
	}
};

export const UPLOAD_SERVICE = {
	async upload(data: any) {
		return await uploadFile(data);
	},

	async upload_ocr(file: any) {
		try {
			const formData = new FormData();
			formData.append('file', file);
			const res = await axios.post(`${WEB_VALUE}/ocr/upload`,
				formData, { headers: { 'Accept': 'multipart/form-data, *' } });
			let data = res.data;
			return data;
		} catch (error) {
			return {
				status: 'error',
				data: error
			}
		}
	}
};