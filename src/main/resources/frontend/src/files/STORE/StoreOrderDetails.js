import React, { useState, useEffect } from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import AcceptButton from "./AcceptButton";
import AddOrderRow from "./AddOrderRow";
import Footer from "../Footer";



const orderUrl = 'http://localhost:8181/orders/'


const StoreOrderDetails = () => {

  const [data, setData] = useState([])

  const [order, setOrder] = useState({})

  const orderId = useParams()

  const navigate = useNavigate()

  
  useEffect(() =>{
                  axios(orderUrl.concat(orderId.id))
                          .then(response => {
                            setData(response.data)})
                          .catch(err => console.log(err))
    axios(orderUrl + "info/" + orderId.id)
    .then(response => setOrder(response.data))
    .catch(err => console.log(err))
              },[]) 

  const removeRow = (id, q, index) => {

    axios.delete("http://localhost:8181/orders/rows", {data:
    {
      ownerId:orderId.id, 
      productId:id, 
      quantity:q}})
           .then(response => console.log(response.status))
                      
    let dataRows = data.filter( item => item.id !== id)
    setData(dataRows)
  }

   const ProductList = data.map((item, index)=>{
    console.log(item)
    return(
            <tr key = {index}>
              <td>{item.productName}</td>
              <td>{item.productType}</td>
              <td>{item.quantity}</td>
              <td>
                <button type="button" className="btn btn-danger" onClick = {e => removeRow(item.id, item.quantity, e)}>delete</button>
              </td>
            </tr>
  )})        
  return (
    <div className="d-flex flex-column min-vh-100">
        <div className="container w-50">
            <h2 className = "mt-5">Order № {orderId.id}</h2>
            <AddOrderRow />
            <table className="table table-dark table-striped align-middle">
              <thead>
                <tr>
                  <th scope="col">Product</th>
                  <th scope="col">Type</th>
                  <th scope="col">Quantity</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {ProductList}
              </tbody>
            </table>
            <div className="container m-3 d-flex justify-content-center">
              <AcceptButton orderId = {orderId.id} storeId = {order.store}/>
              <button className='btn btn-info m-3' onClick={() => navigate(-1)}>BACK</button>
            </div>
        </div>
        <Footer />
    </div>  

   )
}

export default StoreOrderDetails;