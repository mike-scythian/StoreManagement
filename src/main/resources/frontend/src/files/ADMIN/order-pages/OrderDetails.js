import React, { useState, useEffect } from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import PushButton from "./PushButton";



const orderUrl = 'http://localhost:8181/orders/'


const OrderDetails = (flag) => {

  const [data, setData] = useState([])

  const orderId = useParams()

  const navigate = useNavigate()

  
  useEffect(() =>{
                  axios(orderUrl.concat(orderId.id))
                          .then(response => {
                            setData(response.data)})
                          .catch(err => console.log(err))
              },[]) 
                  
   const Rows = data.map((item, index)=>{
    return(
            <tr key = {index}>
              <td>{item.productName}</td>
              <td>{item.productType}</td>
              <td>{item.quantity}</td>
            </tr>
  )})        
  return (
    <div className="container w-50">
        <h2>Order № {orderId.id}</h2>
        <table className="table table-dark table-striped align-middle">
          <thead>
            <tr>
              <th scope="col">Product</th>
              <th scope="col">Type</th>
              <th scope="col">Quantity</th>
            </tr>
          </thead>
          <tbody>
            {Rows}
          </tbody>
        </table>
        <div className="container m-3 d-flex justify-content-center">
          <PushButton id = {orderId.id}/>
        </div>
      </div>
      

   )
}

export default OrderDetails;