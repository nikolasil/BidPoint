import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NotFound from './components/pages/NotFound/NotFound';
import PrivateRoute from './privateRoutes/PrivateRoute';
import { useDispatch } from 'react-redux';
import UnderConstruction from './components/pages/UnderConstruction/UnderConstruction';
import { loadUser } from './actions/auth/auth';
import NavBar from "./components/ui/NavBar";

const Customer = () => {
	const dispatch = useDispatch();
	useEffect(() => {
		dispatch(loadUser());
	}, []);

	return (
	<>
		 {/*<Flex flexDir={'column'} h={'100%'}>*/}
			<NavBar />
			<Routes>
				<Route exact path='/' element={<UnderConstruction />} />
				{/*<Route exact path='/login' element={<Login />} />*/}
				{/*<Route exact path='/register' element={<Register />} />*/}
				{/*<Route exact path='/applications' element={<Applications />} />*/}
				{/*<Route exact path='/announcements' element={<UnderConstruction />} />*/}
				{/*<Route exact path='/faq' element={<UnderConstruction />} />*/}
				{/*<Route exact path='/info' element={<UnderConstruction />} />*/}
				{/*<Route exact path='/contact' element={<UnderConstruction />} />*/}
				{/*<Route exact path='/important-links' element={<UnderConstruction />} />*/}

				{/*<Route*/}
				{/*	exact*/}
				{/*	path='/account'*/}
				{/*	element={*/}
				{/*		<PrivateRoute>*/}
				{/*			<Account />*/}
				{/*		</PrivateRoute>*/}
				{/*	}*/}
				{/*/>*/}
				{/*<Route*/}
				{/*	exact*/}
				{/*	path='/applications/new-application'*/}
				{/*	element={*/}
				{/*		<PrivateRoute>*/}
				{/*			<NewApplication />*/}
				{/*		</PrivateRoute>*/}
				{/*	}*/}
				{/*/>*/}
				{/*<Route*/}
				{/*	path='/applications/:id'*/}
				{/*	element={*/}
				{/*		<PrivateRoute>*/}
				{/*			<Application />*/}
				{/*		</PrivateRoute>*/}
				{/*	}*/}
				{/*/>*/}
				<Route path='*' element={<NotFound />} />
			</Routes>
		{/*</Flex>*/}
	</>
	);
};

export default Customer;
