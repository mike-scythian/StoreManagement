import React, { useState, useEffect } from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import ReactPaginate from "react-paginate";



const baseUrl = 'http://localhost:8181/products?page=';


const Products = () => {

  const [data, setData] = useState([]);

  const navigate = useNavigate();

  const [page,setPage] = useState(0)

  useEffect(() =>{
                  axios.get(baseUrl.concat(page))
                          .then(response => {
                      	    console.log(response.data)
                            setData(response.data)
                            })
                          .catch(err => console.log(err))
              }, [])

  const removeProduct = (id, index) => {

      axios.delete(baseUrl+"/"+id)
            .then(response => {
              console.log(response.status)
            })
            
      let dataRows = data.filter( item => item.id !== id)
      setData(dataRows)
    }

    const handlePageClick = (page) =>{
      console.log("click")
      axios(baseUrl+page.selected)
          .then(response => {
            console.log(response.data)
            setData(response.data)
            })
          .catch(err => console.log(err))
    }      

    const Rows = data.map( (productRow, index) => {
                          return (
                            <tr key = {index}>
                              <td>{productRow.id}</td>
                              <td>{productRow.name}</td>
                              <td>{productRow.type}</td>
                              <td>{productRow.price}</td>
                              <td>{productRow.units}</td>
                              <td>
                                <div className="d-grid gap-2 d-md-flex justify-content-md-end">
                                  <button type="button" className="btn btn-warning" onClick = {() => navigate("/products/update/"+productRow.id)}>edit</button>
                                  <button type="button" className="btn btn-danger" onClick = {e => removeProduct(productRow.id, e)}>delete</button>
                                </div>
                              </td>
                            </tr>
                          )})

  return (
    <div className="container w-50">
      <h2>Products</h2>
        <table className="table table-dark table-striped align-middle">
          <thead>
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Name</th>
              <th scope="col">Type</th>
              <th scope="col">Price</th>
              <th scope="col">Units</th>
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
          <button type="button" className="btn btn-success m-3" onClick={() => navigate("/products/new")}>NEW PRODUCT</button>
        </div>
      </div>

   )
}

export default Products;