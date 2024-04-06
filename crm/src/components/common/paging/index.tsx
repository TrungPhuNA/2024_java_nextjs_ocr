"use client";

import { range } from "@/services/helpers.service";
import { useEffect, useState } from "react";

export const PagingPage = (props: any) => {

	const { total, page, page_size } = props.paging;
	const [paginationMemo, setPaginationMemo]: any = useState([]);

	useEffect(() => {
		if (page) {
			let data = paginationFunc(total, page_size, 1, page) || [];
			console.log(paginationFunc(total, page_size, 1, page));
			setPaginationMemo(data);
		}
	}, [page, total]);
	const paginationFunc = (total: any, page_size: any, siblingCount: any, page: any) => {
		const totalPageCount = Math.ceil(total / page_size);

		// Pages count is determined as siblingCount + firstPage + lastPage + currentPage + 2*DOTS
		const totalPageNumbers = siblingCount + 5;

		let max = siblingCount;


		/*
		  Case 1:
		  If the number of pages is less than the page numbers we want to show in our
		  paginationComponent, we return the range [1..totalPageCount]
		*/
		if (totalPageNumbers >= totalPageCount) {
			return range(1, totalPageCount);
		}

		/*
			Calculate left and right sibling index and make sure they are within range 1 and totalPageCount
		*/
		const leftSiblingIndex = Math.max(page - siblingCount, 1);
		const rightSiblingIndex = Math.min(
			page + siblingCount,
			totalPageCount
		);


		/*
		  We do not show dots just when there is just one page number to be inserted between the extremes of sibling and the page limits i.e 1 and totalPageCount. Hence we are using leftSiblingIndex > 2 and rightSiblingIndex < totalPageCount - 2
		*/
		const shouldShowLeftDots = leftSiblingIndex > 2;
		const shouldShowRightDots = rightSiblingIndex < totalPageCount - 2;

		const firstPageIndex = 1;

		/*
			Case 2: No left dots to show, but rights dots to be shown
		*/
		console.log(shouldShowLeftDots, shouldShowRightDots);
		if (!shouldShowLeftDots && shouldShowRightDots) {
			let leftItemCount = 3 + 2 * siblingCount;
			let leftRange = range(1, leftItemCount);
			return [...leftRange];
		}

		/*
			Case 3: No right dots to show, but left dots to be shown
		*/
		if (shouldShowLeftDots && !shouldShowRightDots) {

			let rightItemCount = 3 + 2 * siblingCount;
			let rightRange = range(
				totalPageCount - rightItemCount + 1,
				totalPageCount
			);
			return [...rightRange];
		}

		/*
			Case 4: Both left and right dots to be shown
		*/
		if (shouldShowLeftDots && shouldShowRightDots) {
			let middleRange = range(leftSiblingIndex, rightSiblingIndex);
			return [...middleRange];
		}

		return [];
	};

	const onNext = () => {
		props.onPageChange(page + 1);
	};

	const onPrevious = () => {
		props.onPageChange(page - 1);
	};

	const lastPage = paginationMemo.length > 0 && paginationMemo[paginationMemo.length - 1] || 0;

	return <div className="flex items-center justify-between border-t border-gray-200 bg-white px-4 py-3 sm:px-6">
		{/* <div className="flex flex-1 justify-between sm:hidden">
			<a href="javascript:void(0)" className="relative inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50">Previous1</a>
			<a href="javascript:void(0)" className="relative ml-3 inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50">Next1</a>
		</div> */}
		<div className="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">
			<div>
				<p className="text-sm text-gray-700">
					totals: <b>{total} results</b>
				</p>
			</div>
			<div>
				<nav className="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
					<a href="javascript:void(0)" onClick={
						() => {
							if (page > 1) {
								onPrevious()
							}
						}
					} className="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">
						<span className="sr-only">Previous</span>
						<svg className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
							<path fillRule="evenodd" d="M12.79 5.23a.75.75 0 01-.02 1.06L8.832 10l3.938 3.71a.75.75 0 11-1.04 1.08l-4.5-4.25a.75.75 0 010-1.08l4.5-4.25a.75.75 0 011.06.02z" clipRule="evenodd" />
						</svg>
					</a>
					{
						paginationMemo?.length > 1 && paginationMemo.map((item: any, key: number) => {
							return <a href="javascript:void(0)"
								className={`
								relative inline-flex items-center px-4 py-2 
								text-sm font-semibold  focus:z-20 
								${page == item ? 
									'bg-indigo-600 text-white focus-visible:outline-indigo-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2' : 
									`text-gray-900 ring-1 ring-inset  ring-gray-300 hover:bg-gray-50 focus:outline-offset-0`}
								`}
								key={key}
								onClick={() => {
									if (page != item) {
										props.onPageChange(item);
									}
								}}>{item}</a>
						})
					}
					{/* className="relative z-10 inline-flex items-center 
							 px-4 py-2 text-sm font-semibold 
							focus:z-20  
							 
							" */}
					<a href="javascript:void(0)"
						onClick={() => {
							if (page < lastPage) {
								onNext()
							}
						}}
						className="relative inline-flex 
						items-center rounded-r-md px-2 py-2 
						text-gray-400 ring-1 ring-inset ring-gray-300 
						hover:bg-gray-50 focus:z-20 focus:outline-offset-0">
						<span className="sr-only">Next</span>
						<svg className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
							<path fillRule="evenodd" d="M7.21 14.77a.75.75 0 01.02-1.06L11.168 10 7.23 6.29a.75.75 0 111.04-1.08l4.5 4.25a.75.75 0 010 1.08l-4.5 4.25a.75.75 0 01-1.06-.02z" clip-rule="evenodd" />
						</svg>
					</a>
				</nav>
			</div>
		</div>
	</div>

}