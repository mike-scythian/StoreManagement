import axios from 'axios';
import React, {useState } from 'react';
import { useNavigate } from 'react-router-dom';


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
		<div className= "container m-5">
			<div className="container m-3 ">
				<button className='btn btn-info mb-5' onClick={() => navigate(-1)}>BACK</button>
				<h2>Create new password</h2>
				<form onSubmit={handlerSubmit}>
					<div className="row">
						<div className="col-6 p-3">
							<div className="form-group">
								<label htmlFor="new-pswd" className="form-label mt-4 ">New password</label>
								<input type="password" className="form-control" id="new-pswd" name="newPassword" onChange={handlerInput} placeholder="new password" />
								<label htmlFor="current-pswd" className="form-label mt-4">Current password</label>
								<input type="password" className="form-control" id="current-pswd" name="oldPassword" onChange={handlerInput} placeholder="current password" />
							</div>
						</div>
					</div>
					<div className="container m-3">
						<button type="submit" className="btn btn-success">SAVE</button>
					</div>
				</form>
			</div>
		</div>
	)
}

export default PasswordUpdate;
