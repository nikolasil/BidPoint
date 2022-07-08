import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Route, Navigate } from 'react-router-dom';

const PrivateRoute = ({ children }) => {
	// const toast = useToast();
	const auth = useSelector((state) => state.auth);
	const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
	const user = useSelector((state) => state.auth.user);
	const loading = useSelector((state) => state.auth.isLoading);

	useEffect(() => {
		if (!loading && !isAuthenticated) {
			// toast({
			// 	title: 'Πρέπει να συνδεθείτε',
			// 	status: 'warning',
			// 	duration: 4000,
			// 	isClosable: true,
			// });
		}
	}, [auth]);

	return !loading && (isAuthenticated ? children : <Navigate to='/login' />);
};

export default PrivateRoute;
