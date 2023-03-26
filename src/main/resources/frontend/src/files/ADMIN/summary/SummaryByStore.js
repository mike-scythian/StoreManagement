import axios from "axios";
import ReactPaginate from "react-paginate";
import React, { useEffect, useState } from "react";
import SummaryBody from "./SummaryBody";
import { useNavigate, useParams } from "react-router-dom";
import Footer from "../../Footer";

const baseUrl = 'http://localhost:8181/summary/stores/'
const prodUrl = 'http://localhost:8181/products'
const storeUrl = 'http://localhost:8181/stores'

const SummaryByStore =() => {

    const params = useParams()

    const nav = useNavigate()

    const [summary, setSummary] = useState([])

    const [page,setPage] = useState(0)

    const [prod, setProd] = useState({})

    useEffect(()=>{
      setFunc()  
    },[])

    function setFunc(){ (async()=>{
        try{
            var res = await (await axios(baseUrl + params.id + "?page=" + page)).data
            res.map(e => { (async()=>{
                try{
                     e.product = await (await axios(prodUrl + "/" + e.product)).data.name
                     e.store = await (await axios(storeUrl + "/" + e.store)).data.name
                     setProd(e)
                }catch(e){
                    console.log(e)
                }
            })()
        })
        setSummary(res)
        }
        catch(e){
            console.log(e)
        } 
    })()}

    const handlePageClick = (page) =>{
        console.log("click")
        axios(baseUrl + params.id + "?page=" + page.selected)
            .then(response => {
              console.log(response.data)
              setSummary(response.data)
              })
            .catch(err => console.log(err))
      }  

    return(
        <div className="d-flex flex-column min-vh-100">
            <div className="container mt-3 w-50">
                <table className="table table-dark table-striped align-middle">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Product</th>
                            <th scope="col">Payment</th>
                            <th scope="col">Time</th>
                            <th scope="col">Store</th>
                        </tr>
                    </thead>
                    <SummaryBody data={summary}/>
                </table>
                <div className="container m-3 d-flex justify-content-center">
                    <ReactPaginate
                        pageCount={3}
                        marginPagesDisplayed={5}
                        onPageChange={handlePageClick}
                        containerClassName={"pagination"}
                        pageClassName={"page-item"}
                        pageLinkClassName={"page-link"}
                        previousClassName={"page-item"} />
                </div>
                <button className='btn btn-info m-3' onClick={() => nav(-1)}>BACK</button>
            </div>
            <Footer />
        </div>
    )
}

export default SummaryByStore