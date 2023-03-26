import axios from 'axios';
import React, {useState } from 'react';
import Footer from '../../Footer';


const submitUrl = 'http://localhost:8181/products/new';


function NewProduct(){

	const [post, setPost] = useState(
		{
			id : 0,
			name : '',
			price : 0,
			units : '',
			type : ''
		})

	const handlerInput = (event) => {
		setPost(
			{
				...post, [event.target.name] : event.target.value
		})
	}
	function handlerSubmit(event){
		event.preventDefault();
		axios.post(submitUrl, post)
				.then(response => {
					console.log(response);
					window.location = "/products"
				})
				.catch(err => console.log(err))
	}

	return(
		<div className = "container mt-3 d-flex flex-column min-vh-100">
			<h2>Create new product</h2>
			<form onSubmit = {handlerSubmit}>
				<div className="row">
					<div className="col-6 p-3">
						<div className="form-group">
							<label htmlFor="product-name" className="form-label mt-4">Product name</label>
							<input type="text" className="form-control" id="product-name" name = "name"onChange = {handlerInput} placeholder="Enter product name"/>
							<label htmlFor="product-type" className="form-label mt-4">Product type</label>
							<input type="text" className="form-control" id="product-type" name = "type" onChange = {handlerInput} placeholder="Enter product type"/>
							<label htmlFor="product-price" className="form-label mt-4">Product price</label>
							<input type="number" className="form-control" id="product-price" name = "price" onChange = {handlerInput} placeholder="Enter price"/>
							<label htmlFor="exampleSelect1" className="form-label mt-4">Units</label>
							<select defaultValue = "--select units--" className="form-select" id="units" name = "units" onChange = {handlerInput}>
								<option></option>
								<option>KG</option>
								<option>APIECE</option>
							</select>
						</div>
					</div>
				</div>
				<div className="container mt-3">
					<button type="submit" className="w-50 btn btn-success">CREATE</button>
				</div>
			</form>
			<Footer/>
		</div>
	);
};

export default NewProduct;
