import React, { useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { loadAdminUser } from './actions/admin/admin';
import UnderConstruction from "./components/pages/UnderConstruction/UnderConstruction";

const Admin = () => {
	const dispatch = useDispatch();

	useEffect(() => {
		dispatch(loadAdminUser());
	});

	return (
		<>
			<Routes>
				<Route exact path='/' element={<UnderConstruction />} />
			</Routes>
		</>
	);
};

export default Admin;
