import axios from "axios";
import { deleteMethod, getMethod, postMethod, putMethod, uploadFile } from "./apiService.service"
import { URL_API, WEB_VALUE } from "./constant";
import { buildFilter, timeDelay } from "./helpers.service";

export const ORDER_SERVICE = {
	async getList(filters: any) {
		await timeDelay(1000)

		const params = buildFilter(filters);
		return await getMethod(`${URL_API.ORDER}/list`, params);
	},
	async statistic(filters: any) {
		await timeDelay(1000)
		const params = buildFilter(filters);
		return await getMethod(`${URL_API.ORDER}/statistic`, params);
	},
	async store(data: any) {
		await timeDelay(1000)

		return await postMethod(`${URL_API.ORDER}/store`, data);
	},
	async show(id: any) {
		await timeDelay(1000)

		return await getMethod(`${URL_API.ORDER}/show/` + id, {});
	},
	async update(id: any, data: any) {
		await timeDelay(1000)
		return await putMethod(`${URL_API.ORDER}/update/` + id, data);
	},
	async delete(id: any) {
		await timeDelay(1000)
		return await deleteMethod(`${URL_API.ORDER}/delete/` + id);
	},
};

export const CATEGORY_SERVICE = {
	async getList(filters: any) {
		await timeDelay(1000)

		const params = buildFilter(filters);
		return await getMethod(`${URL_API.CATEGORY}/list`, params);
	},
	async store(data: any) {
		await timeDelay(1000)

		return await postMethod(`${URL_API.CATEGORY}/store`, data);
	},
	async show(id: any) {
		await timeDelay(1000)

		return await getMethod(`${URL_API.CATEGORY}/show/` + id, {});
	},
	async update(id: any, data: any) {
		await timeDelay(1000)
		return await putMethod(`${URL_API.CATEGORY}/update/` + id, data);
	},
	async delete(id: any) {
		await timeDelay(1000)
		return await deleteMethod(`${URL_API.CATEGORY}/delete/` + id);
	},
};

export const AUTH_SERVICE = {
	async register(data: any) {
		await timeDelay(1000)
		return await postMethod(`${URL_API.AUTH}/register`, data);
	},
	async login(data: any) {
		await timeDelay(1000)
		return await postMethod(`${URL_API.AUTH}/login`, data);
	},
	async show() {
		await timeDelay(1000)
		return await getMethod(`${URL_API.AUTH}/profile`, {});
	},
	async update(id: any, data: any) {
		await timeDelay(1000)
		return await putMethod(`${URL_API.AUTH}/profile/` + id, data);
	}
};

export const UPLOAD_SERVICE = {
	async upload(data: any) {
		await timeDelay(1000)
		return await uploadFile(data);
	},

	async upload_ocr(file: any) {
		try {
			const formData = new FormData();
			formData.append('file', file);
			const res = await axios.post(`${WEB_VALUE}/ocr/upload`,
				formData, { headers: { 'Accept': 'multipart/form-data, *' } });
				
				return res?.data;

			
		} catch (error) {
			return {
				status: 'error',
				message: error
			}
		}
	},
	async upload_ocrV2(file: any) {
		try {
			const formData = new FormData();
			formData.append('file', file);
			const res = await axios.post(`https://testapi.cloudmersive.com/ocr/image/toText`,
				formData, { headers: { 
					'Accept': 'application/json',
					"Apikey" : "expkey:bsP9d6LJ5KCHSIDtuGaonZ+43Sxxt/j0HTRa+YR53CEPeYEnqN8PoNBQHFiphGgL15KExeqTg6v+J1f+AHvqnTdQTfOAJZWkdEMrrKfoz66NAeM+vVCubDNAHydifmkC"
				 } });
			let data = res;
			return data;
		} catch (error) {
			return {
				status: 'error',
				data: error
			}
		}
	}

};