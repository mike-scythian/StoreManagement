import axios from 'axios';
import React, {useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';


const submitUrl = 'http://localhost:8181/orders/rows/'
const productUrl = 'http://localhost:8181/products'



export default function AddOrderRow(){

    const param = useParams()

	const [row, setRow] = useState(
		{
			ownerId : param.id,
			productId : -1,
			quantity : -1
		})
    const [products, setProducts] = useState([])

    useEffect((() => {
        axios(productUrl)
            .then(response => {
                console.log(response.data)
                setProducts(response.data)
            })
            .catch(err => console.log(err))
    }),[])

    const handlerInput = (event) => {
            setRow(
                {
                    ...row, [event.target.name] : event.target.value
            })  
	}

    const ProductList = products.map((prod, index) => {
        return (
                <option value={prod.id} key={index}>{prod.name} {prod.price}</option>
        )})

	function handlerSubmit(event){
		event.preventDefault();
        console.log(row)
		axios.post(submitUrl + param.id, row)
				.then(response => {
					console.log(response.data);
				})
				.catch(err => console.log(err))
        window.location.reload(false)        
	}

	return(
		<div className = "container mt-3 w-50">
			<h4 className="d-flex justify-content-center">Add new row</h4>
			<form onSubmit = {handlerSubmit}>
                <div className="input-group m-3">
                    <select defaultValue = "--select product--" className="custom-select" name="productId" onChange={handlerInput}>
                        <option></option>
                         {ProductList}
                            </select>
                    <input type="number" name="quantity" className="form-control p-2" id="product-quantity" onChange={handlerInput}/>
                </div>
				<div className="d-flex justify-content-center">
				    <button type="submit" className="w-50 mb-3 btn btn-success">ADD</button>
				</div>
			</form>
		</div>
	);
};
