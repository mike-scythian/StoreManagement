import axios from 'axios';
import React, {useEffect, useState } from 'react';
import Footer from "../../Footer";


const submitUrl = 'http://localhost:8181/users/new';
const storeUrl =  'http://localhost:8181/stores';



function NewUser(){

	const [user, setUser] = useState(
		{
			id : -1,
			firstName : '',
			lastName : '',
			email : '',
			password : '',
            roles : '',
            store : -1
		})
    const [stores, setStores] = useState([])

    useEffect((() => {
        axios(storeUrl)
            .then(response => {
                console.log(response.data)
                setStores(response.data)
            })
            .catch(err => console.log(err))
    }),[])

    const StoreList = stores.map((store, index) => {
        return (
                <option value={store.id} key={index}>{store.name}</option>
        )})

	const handlerInput = (event) => {
		setUser(
			{
				...user, [event.target.name] : event.target.value
		})
	}
	function handlerSubmit(event){
		event.preventDefault();
		axios.post(submitUrl, user)
				.then(response => {
					console.log(response);
					window.location = "/users"
				})
				.catch(err => console.log(err))
	}

	return(
		<div className="d-flex flex-column min-vh-100">
			<div className = "container mt-3 ">
				<h2>Create new user</h2>
				<form onSubmit = {handlerSubmit}>
					<div className="row">
						<div className="col-6 p-3">
							<div className="form-group">
								<label htmlFor="product-name" className="form-label mt-4">Name</label>
								<input type="text" className="form-control" id="user-name" name = "firstName" onChange = {handlerInput} placeholder="Enter name"/>
								<label htmlFor="product-type" className="form-label mt-4">Last name</label>
								<input type="text" className="form-control" id="user-lastName" name = "lastName" onChange = {handlerInput} placeholder="Enter last name"/>
								<label htmlFor="product-price" className="form-label mt-4">Email</label>
								<input type="email" className="form-control" id="user-email" name = "email" onChange = {handlerInput} placeholder="Enter email"/>
								<label htmlFor="product-price" className="form-label mt-4">Password</label>
								<input type="password" className="form-control" id="password" name = "password" onChange = {handlerInput} placeholder="Enter password"/>
								<label htmlFor="exampleSelect1" className="form-label mt-4">Role</label>
								<select defaultValue = "--select role--" className="form-select" id="role" name = "roles" onChange = {handlerInput}>
									<option></option>
									<option>ROLE_ADMIN</option>
									<option>ROLE_USER</option>
								</select>
								<label htmlFor="exampleSelect1" className="form-label mt-4">Store</label>
								<select defaultValue = "--select store--" className="form-select" id="store" name = "store" onChange = {handlerInput}>
									<option></option>
									{StoreList}
								</select>
							</div>
						</div>
					</div>
					<div className="container mt-3">
						<button type="submit" className="w-50 btn btn-success">CREATE</button>
					</div>
				</form>
			</div>
			<Footer />
		</div>
	);
};

export default NewUser;
