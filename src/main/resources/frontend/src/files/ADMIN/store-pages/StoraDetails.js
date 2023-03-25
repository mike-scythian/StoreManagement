import React, { useState, useEffect } from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import Footer from "../../Footer";



const storeUrl = 'http://localhost:8181/stores/'

const usersUrl =  'http://localhost:8181/users'


const StoreDetails = () => {

  const [data, setData] = useState([])

  const [sellers, setSellers] = useState([])

  const [leftovers, setLeftovers] = useState([])

  const [users, setUsers] = useState([])

  const navigate = useNavigate();

  const storeId = useParams()

  var selectedUser = -1
  
  useEffect(() =>{
                axios(storeUrl.concat(storeId.id))
                          .then(response => {
                            setData(response.data)
                            setSellers(response.data.sellers)})
                          .catch(err => console.log(err))
                axios(storeUrl + "stocks/" + storeId.id)
                            .then(response => {
                                setLeftovers(response.data)
                            })
                            .catch(err => console.log(err))
                axios(usersUrl)
                            .then(response => {
                                setUsers(response.data)
                            })
                            .catch(err => console.log(err))
              },[])

  const SellerList = sellers.map((seller, index) => {
    return(
        <ul className="list-group" key={index}>
            <li className="list-group-item">{seller.firstName} {seller.lastName} {seller.email}</li>
        </ul>
    )})

    const Body = leftovers.map((item, index)=>{
        return(
                <tr key = {index}>
                  <td>{item.productName}</td>
                  <td>{item.productType}</td>
                  <td>{item.quantity}</td>
                </tr>
    )})

    const UserList = users.map((user, index) => {
        return (
                <option value={user.id} key={index}>{user.firstName} {user.lastName}</option>
        )})

    function setupSeller(event){
        event.preventDefault()
        axios.patch(usersUrl+ "/"+ selectedUser + "?newStoreId=" + storeId.id, [])
            .then(response => {
                console.log(response.status)
            })
            .catch(error => console.log(error))

        window.location.reload()  
    }

    function getUserId(event){
        selectedUser = event.target.value
    }

    function removeStore (){

        axios.delete(storeUrl.concat(storeId.id))
              .then(response => {
                console.log(response.status)
                navigate("/stores")
              })
      }
                  
  return (
   <div className="container d-flex flex-column min-vh-100">
        <div className="container w-50 card d-flex justify-content-center mt-3">

            <header>
                <h4 className="card-header mb-2">{data.name}</h4>
            </header>

            <div className="container">
                <p>Opened {data.openDate}</p>
                <hr/>
                <p><strong>Total income</strong> : {data.income}</p>
                <hr/>
                <h4>Sellers:</h4>
                {SellerList}
                <form onSubmit={setupSeller}>
                <h5 className="m-3">All sellers : </h5>
                <div className="container">
                    <div className="row">
                        <select defaultValue = "--select user--" className="custom-select mb-3" name="option" onChange={getUserId}>
                            {UserList}
                        </select>
                            <button type = "submit" className="btn btn-success mt-3 mb-3" >INSTALL SELLER</button>
                        </div>
                    </div> 
                </form>
            </div>
        </div>
        <div className="container d-flex justify-content-center m-3">
            <button className="w-25 btn btn-warning m-3" onClick={() => navigate("/stores/update/"+storeId.id)}>CHANGE NAME</button>
            <button className="w-25 btn btn-danger m-3" onClick={removeStore}>DELETE STORE</button>
        </div>
        <div className="container d-flex justify-content-center w-50">
            <table className="table table-dark text-center">
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
        </div>
            < Footer />
        </div>
   )
}

export default StoreDetails;