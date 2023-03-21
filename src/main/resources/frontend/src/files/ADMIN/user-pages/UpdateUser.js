import axios from 'axios';
import React, {useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';


const baseUrl = 'http://localhost:8181/users';


function UpdateUser(){

    const localId = useParams()

	const [user, setUser] = useState(
		{
			id: -1,
  			firstName: "",
			lastName: "",
			email: "",
			roles: "",
			storeId: -1
		})

    useEffect( () => {

        axios(baseUrl +"/"+localId.id)
            .then(response => {
                console.log(response.data)
                setUser(response.data)
            })
            .catch(error => console.error(error))

    }, [])

	const handlerInput = (event) => {
		setUser(
			{
				...user, [event.target.name] : event.target.value
		})
	}
	function handlerUpdate(event){
		event.preventDefault();
        console.log(user)
		axios.put(baseUrl , user)
				.then(response => {
					console.log(response);
					window.location = "/users"
				})
				.catch(err => console.log(err))
	}

	return(
		<div className = "container mt-3">
			<h2>Update user</h2>
			<form onSubmit = {handlerUpdate}>
				<div className="row">
					<div className="col-6 p-3">
                    <div className="form-group">
							<label htmlFor="firstName" className="form-label mt-4">Name</label>
							<input type="text" className="form-control" id="user-name" name = "firstName" onChange = {handlerInput} value={user.firstName}/>
							<label htmlFor="lastName" className="form-label mt-4">Last name</label>
							<input type="text" className="form-control" id="user-lastName" name = "lastName" onChange = {handlerInput} value={user.lastName}/>
							<label htmlFor="role" className="form-label mt-4">Role</label>
							<select defaultValue = "--select role--" className="form-select" id="roles" name = "roles" onChange = {handlerInput}>
								<option></option>
								<option>ROLE_ADMIN</option>
								<option>ROLE_USER</option>
							</select>
						</div>
					</div>
				</div>
				<div className="container mt-3">
					<button type="submit" className="btn btn-success">UPDATE</button>
				</div>
			</form>
		</div>
	);
};

export default UpdateUser;
