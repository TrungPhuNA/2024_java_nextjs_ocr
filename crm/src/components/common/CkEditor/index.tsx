"use client";
import { setField } from "@/services/helpers.service";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import React, { useEffect, useState } from "react";
import '../../../assets/style.css'

const CkeditorPage = (props: any) => {
	return (
		<div>
			{props.title && <label className="mb-3 block text-xl text-sm font-medium text-black dark:text-white">
				{props.title || 'Mô tả'}
			</label>}

			<div className="relative z-20 bg-white dark:bg-form-input">
				<CKEditor
					editor={ClassicEditor}
					data={props.value}
					onChange={(e, editor) => {
						setField(editor?.getData(), props.key_obj, props.form, props.setForm)
					}}
				/>
			</div>
		</div>
	);
};

export default CkeditorPage;
