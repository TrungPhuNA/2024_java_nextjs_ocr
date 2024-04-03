import Link from "next/link";
interface BreadcrumbProps {
	pageName: string;
	subName?: string;
	is_hide?: boolean
}
const Breadcrumb = (props: BreadcrumbProps) => {
	if(props.is_hide) {
		return null;
	}
	return (
		<div className="mb-6  gap-3 ">
			<nav>
				<ol className="flex items-center gap-2">
					<li>
						<Link className="font-medium" href="/">
							Dashboard /
						</Link>
					</li>
					<li className="font-medium text-primary">{props.subName || props.pageName}</li>
				</ol>
			</nav>
			<h2 className="text-title-md2 font-semibold text-black dark:text-white">
				{props.pageName}
			</h2>
		</div>
	);
};

export default Breadcrumb;
