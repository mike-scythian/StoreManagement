import React, { useState, useEffect } from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import ReactPaginate from "react-paginate";


const baseOrderUrl = 'http://localhost:8181/orders?page=';


const Orders = () => {

  const [data, setData] = useState([])

  const navigate = useNavigate()

  const [page,setPage] = useState(0)

  useEffect(() =>{
                axios.get(baseOrderUrl+page)
                .then(response => {
                  console.log(response.data)
                  setData(response.data)
                  })
                .catch(err => console.log(err))        
              }, [])
              
  const handlePageClick = (page) =>{
      console.log("click")
      axios(baseOrderUrl+page.selected)
            .then(response => {
                console.log(response.data)
                setData(response.data)
            })
            .catch(err => console.log(err))
    }  

const handleSortByDate = () => {
  axios.get(baseOrderUrl+page+"&sortParam=createTime")
                .then(response => {
                  console.log(response.data)
                  setData(response.data)
                  })
                .catch(err => console.log(err))        
              }

const handleSortByStatus = () => {
  axios.get(baseOrderUrl+page+"&sortParam=status")
       .then(response => {
              console.log(response.data)
              setData(response.data)
     }).catch(err => console.log(err))}

  const Rows = data.map( (orderRow, index) => {
                          return (
                            <tr key = {index}>
                              <td>{orderRow.id}</td>
                              <td>{orderRow.createTime}</td>
                              <td>{orderRow.store}</td>
                              <td>{orderRow.status}</td>
                              <td>
                                <button type="button" className="btn btn-warning" onClick = {()=>navigate("/orders/"+orderRow.id)}>details</button>
                              </td>
                            </tr>
                          )})
  return (
    <div className="container w-50">
      <h2>Orders</h2>
       <table className="table table-dark table-striped align-middle">
        <thead>
           <tr>
            <th scope="col">ID</th>
            <th scope="col">CREATE TIME</th>
            <th scope="col">STORE</th>
            <th scope="col">STATUS</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
            {Rows}
        </tbody>
      </table>
      <div className="container m-3 d-flex justify-content-center">
          <ReactPaginate
            pageCount={5}
            marginPagesDisplayed={5}
            onPageChange={handlePageClick}
            containerClassName={"pagination"}
            pageClassName={"page-item"}
            pageLinkClassName={"page-link"}
            previousClassName={"page-item"}/>
        </div>
        <div className="container m-3 d-flex justify-content-center">
          <button type="button" className="btn btn-primary m-3" onClick={handleSortByDate}>Sort by date</button>
          <button type="button" className="btn btn-primary m-3" onClick={handleSortByStatus}>Sort by status</button>
        </div>
    </div>

   )
  }

export default Orders;