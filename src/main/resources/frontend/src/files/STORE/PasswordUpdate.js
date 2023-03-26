import axios from 'axios';
import React, {useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Footer from '../Footer';


const submitUrl = 'http://localhost:8181/users/password';


function PasswordUpdate(){

	const navigate = useNavigate()

	const [pswdRequest, setPswdRequest] = useState({
        newPassword: '',
        oldPassword: ''
    })

	const handlerInput = (event) => {
		setPswdRequest({...pswdRequest , [event.target.name] : event.target.value})
		console.log(pswdRequest.newPassword + ' ' + pswdRequest.oldPassword)
    }
    
	function handlerSubmit(event){
		event.preventDefault();
		axios.patch(submitUrl, pswdRequest)
				.then(response => {
					console.log(pswdRequest);
				})
				.catch(err => console.log(err))
        alert("Password updated")
	}

	return(
		<div className= "container m-3 d-flex flex-column min-vh-100">
			<div className="container">
				<button className='btn btn-info mb-5' onClick={() => navigate(-1)}>BACK</button>
				<h2>Create new password</h2>
				<form onSubmit={handlerSubmit}>
					<div className="row">
					
						<div className="col-4 p-3">
							<div className="form-group">
								<label htmlFor="new-pswd" className="form-label mt-4 ">New password</label>
								<input type="password" className="form-control" id="new-pswd" name="newPassword" onChange={handlerInput} placeholder="new password" />
								<label htmlFor="current-pswd" className="form-label mt-4">Current password</label>
								<input type="password" className="form-control" id="current-pswd" name="oldPassword" onChange={handlerInput} placeholder="current password" />
							</div>
						</div>
					</div>
					
						<button type="submit" className="m-4 w-25 btn btn-success">SAVE</button>
				
				</form>
			</div>
			<Footer />
		</div>
	)
}

export default PasswordUpdate;
