const Loader = (props: any) => {
  return (
    <div className={`flex h-screen items-center justify-center ${props.className || 'bg-white'}  dark:bg-black`}>
      <div className="h-16 w-16 animate-spin rounded-full border-4 border-solid border-primary border-t-transparent"></div>
    </div>
  );
};

export default Loader;
