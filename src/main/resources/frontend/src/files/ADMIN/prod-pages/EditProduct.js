import axios from 'axios';
import React, {useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Footer from '../../Footer';


const baseUrl = 'http://localhost:8181/products';


function EditProduct(){

    const localId = useParams()

	const [product, setProduct] = useState(
		{
			id : 0,
			name : '',
			price : 0,
			units : '',
			type : ''
		})

    useEffect( () => {

        axios(baseUrl+"/"+localId.id)
            .then(response => {
                console.log(response.data)
                setProduct(response.data)
            })
            .catch(error => console.error(error))
    }, [])

	const handlerInput = (event) => {
		setProduct(
			{
				...product, [event.target.name] : event.target.value
		})
	}
	function handlerUpdate(event){
		event.preventDefault();
		axios.put(baseUrl+"/update/"+localId.id, product)
				.then(response => {
					console.log(response);
					window.location = "/products"
				})
				.catch(err => console.log(err))
	}

	return(
		<div className = "container mt-3 d-flex flex-column min-vh-100">
			<h2>Update product {product.name}</h2>
			<form onSubmit = {handlerUpdate}>
				<div className="row">
					<div className="col-6 p-3">
						<div className="form-group">
							<label htmlFor="product-name" className="form-label mt-4">Product name</label>
							<input type="text" className="form-control" id="product-name" name = "name"onChange = {handlerInput} value = {product.name}/>
							<label htmlFor="product-type" className="form-label mt-4">Product type</label>
							<input type="text" className="form-control" id="product-type" name = "type" onChange = {handlerInput} value = {product.type}/>
							<label htmlFor="product-price" className="form-label mt-4">Product price</label>
							<input type="number" className="form-control" id="product-price" name = "price" onChange = {handlerInput} value = {product.price}/>
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
					<button type="submit" className="w-50 btn btn-success">UPDATE</button>
				</div>
			</form>
			<Footer />
		</div>
	);
};

export default EditProduct;
