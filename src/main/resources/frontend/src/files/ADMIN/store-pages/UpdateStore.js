import axios from 'axios';
import React, {useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Footer from '../../Footer';


const baseUrl = 'http://localhost:8181/stores/';


function UpdateStore(){

    const localId = useParams()

	const [storeName, setName] = useState({ name : '' })

    useEffect( () => {

        axios(baseUrl.concat(localId.id))
            .then(response => {
                console.log(response.data)
                setName(response.data.name)
            })
            .catch(error => console.error(error))
    }, [])

	const handlerInput = (event) => {
		setName(
			{
				...storeName, [event.target.name] : event.target.value
		})
	}
	function handlerUpdate(event){
		event.preventDefault();
		axios.patch(baseUrl.concat("/update/"+ localId.id) + "?newName=" + storeName.name, [])
				.then(response => {
					console.log(response);
					window.location = "/stores"
				})
				.catch(err => console.log(err))
	}

	return(
		<div className = "container mt-3 d-flex flex-column min-vh-100">
			<h2>Update store name</h2>
			<form onSubmit = {handlerUpdate}>
				<div className="row">
					<div className="col-6 p-3">
						<div className="form-group">
							<label htmlFor="product-name" className="form-label mt-4">Store name</label>
							<input type="text" className="form-control" id="store-name" name = "name" onChange = {handlerInput} value = {storeName.name}/>
						</div>
					</div>
				</div>
				<div className="container mt-3">
					<button type="submit" className="btn btn-success">UPDATE</button>
				</div>
			</form>
			<Footer />
		</div>
	);
};

export default UpdateStore;
