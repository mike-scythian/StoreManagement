import React, { useState, useEffect } from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";



const storeUrl = 'http://localhost:8181/stores/stocks/'


const StoreStock = () => {

  const [leftovers, setLeftovers] = useState([])

  const navigate = useNavigate();

  const storeId = useParams()

  const [saleRequest, setSaleRequest] = useState({
    ownerId:storeId.id,
    productId:-1,
    quantity:-1
  })

  
  useEffect(() =>{
    axios(storeUrl + storeId.id)
      .then(response => setLeftovers(response.data))
      .catch(err => console.log(err))
  },[])

    const Body = leftovers.map((item, index)=>{
        return(
                <tr key = {index}>
                  <td>{item.productName}</td>
                  <td>{item.productType}</td>
                  <td>{item.quantity}</td>
                </tr>
    )})

    const ProdList = leftovers.map((prod, index) => {
      return(
        <option value={prod.id} key={index}>{prod.productName}</option>
      )})

    function handlerInput(event){
      setSaleRequest(
        {...saleRequest, [event.target.name]:event.target.value}
      )
    }

    function handlerSale(){
      axios.patch('http://localhost:8181/stores/sale', saleRequest)
      .then(response => console.log(response.status))
      window.location.reload(false)
    }
                  
  return (
   <div className="container w-50">        
            <table className="table table-dark table-striped align-middle">
            <thead>
                <tr>
                <th scope="col">Product</th>
                <th scope="col">Type</th>
                <th scope="col">Quantity</th>
                </tr>
            </thead>
            <tbody>
                {Body}
            </tbody>
            </table>
            <div className="d-flex justify-content-center">
              <button type="button" className="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal" >SALE</button> 
            </div>
            <div className="modal fade" id="exampleModal" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div className="modal-dialog">
                <div className="modal-content">
                  <div className="modal-header">
                    <h5 className="modal-title" id="exampleModalLabel">Sale</h5>
                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div className="modal-body d-flex justify-content-center">
                    <select defaultValue = "--select product--" className="custom-select" name="productId" onChange={handlerInput}>
                      <option></option>
                      {ProdList}
                    </select>
                    <input type="number" min="0" className="form-control" id="product-price" name = "quantity" onChange = {handlerInput} placeholder="Enter quantity"/>
                  </div>
                  <div className="modal-footer d-flex justify-content-center">
                    <button type="button" className="btn btn-success" onClick = {handlerSale}>CONFIRM</button>
                  </div>
                </div>
              </div>
            </div> 
    </div>
   )
}

export default StoreStock;