

export const STATIC_URL_IMAGE=process.env.NEXT_PUBLIC_API + 'upload/';
export const STATIC_URL_API=process.env.NEXT_PUBLIC_DOMAIN_API;
export const WEB_VALUE=process.env.NEXT_PUBLIC_API || 'http://localhost:3010';
export const URL = 'http://localhost:4000';
export const URL_API = {
	AUTH: 'auth',
	ORDER: 'order',
	UPLOAD: 'upload',
	CATEGORY: 'category'
};



export const Gender = [
	{
		value: 'NAM',
		label: 'Nam'
	},
	{
		value: 'Nữ',
		label: 'Nữ'
	},
	{
		value: 'Khác',
		label: 'Khác'
	}
];

export const INIT_PAGING = {
	page: 1,
	page_size: 10,
	total_page: 1,
	total: 0
};
