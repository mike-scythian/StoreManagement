import React, { useState, useEffect } from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import ReactPaginate from "react-paginate";
import Footer from "../../Footer";



const baseStoreUrl = 'http://localhost:8181/stores?page=';


const Stores = () => {

  const [data, setData] = useState([]);

  const navigate = useNavigate();

  const [page,setPage] = useState(0)


  useEffect(() =>{
                  axios(baseStoreUrl.concat(page))
                          .then(response => {
                      	    console.log(response.data)
                            setData(response.data)
                            })
                          .catch(err => console.log(err))
              }, [])

  const handlePageClick = (page) =>{
    console.log("click")
    axios(baseStoreUrl+page.selected)
        .then(response => {
          console.log(response.data)
          setData(response.data)
          })
        .catch(err => console.log(err))
  }            

  const Rows = data.map( (row, index) => {
                          return (
                            <tr key = {index}>
                              <td>{row.id}</td>
                              <td>{row.name}</td>
                              <td>{row.openDate}</td>
                              <td>{row.income}</td>
                              <td>
                                <button type="button" className="w-75 btn btn-warning" onClick = {()=>navigate("/stores/"+row.id)}>details</button>
                              </td>
                            </tr>
                          )})

  return (
    <div className="container d-flex flex-column min-vh-100">
      <div className="container w-50">
        <h2>Stores</h2>
          <table className="table table-dark table-striped align-middle">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">NAME</th>
                <th scope="col">OPEN DATE</th>
                <th scope="col">INCOME</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {Rows}
            </tbody>
          </table>
          <div className="container m-3 d-flex justify-content-center">
            <ReactPaginate
              pageCount={3}
              marginPagesDisplayed={5}
              onPageChange={handlePageClick}
              containerClassName={"pagination"}
              pageClassName={"page-item"}
              pageLinkClassName={"page-link"}
              previousClassName={"page-item"}/>
          </div>
          
          <div className="container d-flex justify-content-center">
            <button className="w-25 btn btn-success" onClick={()=>navigate("/stores/new")}>CREATE STORE</button>
          </div>          
      </div>
      <Footer/>
    </div>
   )
}

export default Stores;