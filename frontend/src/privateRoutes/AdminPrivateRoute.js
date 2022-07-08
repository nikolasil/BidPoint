import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Route, Navigate } from 'react-router-dom';

const AdminPrivateRoute = ({ children }) => {
	// const toast = useToast();
	const admin = useSelector((state) => state.admin);
	const isAuthenticated = useSelector((state) => state.admin.isAuthenticated);
	const user = useSelector((state) => state.admin.user);
	const loading = useSelector((state) => state.admin.isLoading);

	useEffect(() => {
		if (!loading && !isAuthenticated) {
			// toast({
			// 	title: 'Πρέπει να συνδεθείτε',
			// 	status: 'warning',
			// 	duration: 4000,
			// 	isClosable: true,
			// });
		}
	}, [admin]);

	return !loading && (isAuthenticated ? children : <Navigate to='/admin' />);
};

export default AdminPrivateRoute;
