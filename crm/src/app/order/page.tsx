import React from "react";
import Link from "next/link";
import Image from "next/image";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";

import { Metadata } from "next";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import TableTwo from "@/components/Tables/TableTwo";

import { Product } from "@/types/product";

const productData: Product[] = [
  {
    image: "/images/product/product-01.png",
    name: "Apple Watch Series 7",
    category: "Electronics",
    price: 296,
    sold: 22,
    profit: 45,
  },
  {
    image: "/images/product/product-02.png",
    name: "Macbook Pro M1",
    category: "Electronics",
    price: 546,
    sold: 12,
    profit: 125,
  },
  {
    image: "/images/product/product-03.png",
    name: "Dell Inspiron 15",
    category: "Electronics",
    price: 443,
    sold: 64,
    profit: 247,
  },
  {
    image: "/images/product/product-04.png",
    name: "HP Probook 450",
    category: "Electronics",
    price: 499,
    sold: 72,
    profit: 103,
  },
];

export const metadata: Metadata = {
	title: "CRM | Order",
	description: "CRM List order",
	// other metadata
};

const OrderList: React.FC = () => {
	return (
		<DefaultLayout>
			<Breadcrumb pageName="Order" />

			<div className="flex flex-col gap-10">
				<div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
					<div className="px-4 py-6 md:px-6 xl:px-7.5 md:flex md:justify-between w-full">
						<h4 className="text-xl font-semibold text-black dark:text-white">
							Danh sách
						</h4>
						<Link href={'/order/form'} className="inline-flex items-center justify-center rounded-md bg-primary px-10 py-4 text-center font-medium text-white hover:bg-opacity-90 lg:px-8 xl:px-10">Create</Link>
					</div>

					<div className="grid grid-cols-6 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5">
						<div className="col-span-3 flex items-center">
							<p className="font-medium">Product Name</p>
						</div>
						<div className="col-span-2 hidden items-center sm:flex">
							<p className="font-medium">Category</p>
						</div>
						<div className="col-span-1 flex items-center">
							<p className="font-medium">Price</p>
						</div>
						<div className="col-span-1 flex items-center">
							<p className="font-medium">Sold</p>
						</div>
						<div className="col-span-1 flex items-center">
							<p className="font-medium">Profit</p>
						</div>
					</div>

					{productData.map((product, key) => (
						<div
							className="grid grid-cols-6 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5"
							key={key}
						>
							<div className="col-span-3 flex items-center">
								<div className="flex flex-col gap-4 sm:flex-row sm:items-center">
									<div className="h-12.5 w-15 rounded-md">
										<Image
											src={product.image}
											width={60}
											height={50}
											alt="Product"
										/>
									</div>
									<p className="text-sm text-black dark:text-white">
										{product.name}
									</p>
								</div>
							</div>
							<div className="col-span-2 hidden items-center sm:flex">
								<p className="text-sm text-black dark:text-white">
									{product.category}
								</p>
							</div>
							<div className="col-span-1 flex items-center">
								<p className="text-sm text-black dark:text-white">
									${product.price}
								</p>
							</div>
							<div className="col-span-1 flex items-center">
								<p className="text-sm text-black dark:text-white">{product.sold}</p>
							</div>
							<div className="col-span-1 flex items-center">
								<p className="text-sm text-meta-3">${product.profit}</p>
							</div>
						</div>
					))}
				</div>
			</div>
		</DefaultLayout>
	);
};

export default OrderList;
