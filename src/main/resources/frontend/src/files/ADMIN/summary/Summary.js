import axios from "axios";
import ReactPaginate from "react-paginate";
import React, { useEffect, useState } from "react";
import SummaryBody from "./SummaryBody";
import TopProducts from "./TopProducts";
import { useNavigate } from "react-router-dom";

const baseUrl = 'http://localhost:8181/summary'
const prodUrl = 'http://localhost:8181/products'
const storeUrl = 'http://localhost:8181/stores'

const Summary =() => {

    const nav = useNavigate()

    const [summary, setSummary] = useState([])

    const [page,setPage] = useState(0)

    const [prod, setProd] = useState({})

    const [topic, setTopic] = useState([])

    const [stores, setStores] = useState([])

    const [products, setProducts] = useState([])

    var [storeId, setStoreId] = useState({ storeId:-1 })

    var [productId, setProductId] = useState({ productId:-1 })

    var [dateForStore, setDateForStore] = useState({start:'', end:''})

    var [dateForProduct, setDateForProduct] = useState({start:'', end:''})
    useEffect(()=>{
      setFunc()  
      getTopic()  
      getStoreList() 
      getProductList()
    },[])

    function setFunc(){ (async()=>{
        try{
            var res = await (await axios(baseUrl + "?page=" + page)).data
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

    function getTopic(){
        axios(baseUrl + "/products/top")
            .then(response => {
                console.log(response.data)
                setTopic(response.data)})
    }

    function getStoreList(){
        axios(storeUrl)
        .then(response => {
            console.log(response.data)
            setStores(response.data)})
    }

    function getProductList(){
        axios(prodUrl)
        .then(response => {
            console.log(response.data)
            setProducts(response.data)})
    }

    function handlerInputStore(event){
        setStoreId({...storeId,[event.target.name]:event.target.value})
    }

    function handlerInputProduct(event){
        setProductId({...productId,[event.target.name]:event.target.value})
    }

    function handlerDateForStore(event){
        setDateForStore({...dateForStore, [event.target.name]:event.target.value})
    }

    function handlerDateForProduct(event){
        setDateForProduct({...dateForProduct, [event.target.name]:event.target.value})
    }

    function handlerByProduct(){
        if(dateForProduct.start === '' || dateForProduct.end === '')
            nav("/summary/products/"+productId.productId)
        else
            nav("/summary/products/by-period/"+productId.productId+"?start="+dateForProduct.start+"&end="+dateForProduct.end) 
    }

    function handlerByStore(){
        if(dateForStore.start === '' || dateForStore.end === '')
            nav("/summary/stores/"+storeId.storeId)
        else
            nav("/summary/stores/by-period/"+storeId.storeId+"?start="+dateForStore.start+"&end="+dateForStore.end) 
    }

    const handlePageClick = (page) =>{
        console.log("click")
        axios(baseUrl + "?page=" + page.selected)
            .then(response => {
              console.log(response.data)
              setSummary(response.data)
              })
            .catch(err => console.log(err))
      }  

      const StoreList = stores.map((store, index) => {
        return (
                <option value={store.id} key={index}>{store.name}</option>
        )}) 
        
        const ProductList = products.map((prod, index) => {
            return (
                    <option value={prod.id} key={index}>{prod.name}</option>
            )})  

    return(
        <div className="container mt-3">

            <div className="container">
                <div className="row">
                    <div className="col">
                        <h4>Statistic</h4>
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
                                pageCount={5}
                                marginPagesDisplayed={5}
                                onPageChange={handlePageClick}
                                containerClassName={"pagination"}
                                pageClassName={"page-item"}
                                pageLinkClassName={"page-link"}
                                previousClassName={"page-item"} />
                        </div>
                    </div>
                    <div className="col">
                        <TopProducts data={topic} />
                    </div>
                </div>
            </div>

            <div className="d-grid gap-2 col-6 mx-start">
                <div className="container m-2">
                    <div className="row justify-content-start">
                        <div className="col-lg-3 col-sm-6 mt-4">
                            <select className="custom-select mt-1" name="storeId" onChange={handlerInputStore}>
                                <option></option>
                                {StoreList}
                            </select>
                        </div>    
                        <div className="col-lg-3 col-sm-6 mt-4">
                            <button className="btn-sm btn-success" type="button" onClick={()=>handlerByStore()}>BY STORE</button>
                        </div>
                        <div className="col-lg-3 col-sm-6">
                            <label htmlFor="startDate">Start</label>
                            <input id="startDate" className="form-control" type="date" name="start" onChange={handlerDateForStore}/>
                            <span id="startDateSelected"></span>
                        </div>
                        <div className="col-lg-3 col-sm-6">
                            <label htmlFor="endDate">End</label>
                            <input id="endDate" className="form-control" type="date" name="end" onChange={handlerDateForStore}/>
                            <span id="endDateSelected"></span>
                        </div>
                    </div>
                </div>    
                <div className="container m-2">
                    <div className="row justify-content-start">
                        <div className="col-lg-3 col-sm-6 mt-4">
                            <select className="custom-select mt-2" name="productId" onChange={handlerInputProduct}>
                                <option></option>
                                {ProductList}
                            </select>
                        </div>    
                        <div className="col-lg-3 col-sm-6 mt-4">
                            <button className="btn-sm btn-success" type="button" onClick={()=>handlerByProduct()}>BY PRODUCT</button>
                        </div>
                        <div className="col-lg-3 col-sm-6">
                            <label htmlFor="startDate">Start</label>
                            <input id="startDate" className="form-control" type="date" name="start" onChange={handlerDateForProduct}/>
                            <span id="startDateSelected"></span>
                        </div>
                        <div className="col-lg-3 col-sm-6">
                            <label htmlFor="endDate">End</label>
                            <input id="endDate" className="form-control" type="date" name="end" onChange={handlerDateForProduct}/>
                            <span id="endDateSelected"></span>
                        </div>
                    </div>        
                </div>
            </div>
        </div>
    )
}

export default Summary