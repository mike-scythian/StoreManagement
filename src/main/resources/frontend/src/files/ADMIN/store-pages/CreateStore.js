import axios from 'axios';
import React, {useState } from 'react';
import Footer from '../../Footer';


const submitUrl = 'http://localhost:8181/stores/new?storeName=';


function CreateStore(){

	const [storeName, setStoreName] = useState({
        name: ''
    })

	const handlerInput = (event) => {
		setStoreName({...storeName , [event.target.name] : event.target.value})
    }
    
	function handlerSubmit(event){
		event.preventDefault();
		axios.post(submitUrl.concat(storeName.name), storeName.name)
				.then(response => {
					console.log(response.data);
					window.location = "/stores"
				})
				.catch(err => console.log(err))
        alert("Store <<" + storeName.name + ">> is created")
	}

	return(
		<div className = "container mt-3 d-flex flex-column min-vh-100">
			<h2>Create new store</h2>
			<form onSubmit = {handlerSubmit}>
				<div className="row">
					<div className="col-6 p-3">
						<div className="form-group">
							<label htmlFor="product-name" className="form-label mt-4">Store name</label>
							<input type="text" className="form-control" id="store-name" name = "name" onChange = {handlerInput} placeholder="Enter store name"/>
						</div>
					</div>
				</div>
				<div className="container mt-3">
					<button type="submit" className="btn btn-success">CREATE</button>
				</div>
			</form>
			< Footer />
		</div>
	);
};

export default CreateStore;
