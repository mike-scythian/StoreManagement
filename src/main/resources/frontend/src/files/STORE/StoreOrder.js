import { useEffect, useState } from "react"
import {useNavigate, useParams} from "react-router-dom"
import ReactPaginate from "react-paginate";
import axios from "axios";
import LayoutStore from "./LayoutStore";

const baseUrl = 'http://localhost:8181/orders/store/'


const StoreOrder = () => {

    const idParam = useParams()
    const navigate = useNavigate()

    const [data, setData] = useState([])
    const [page,setPage] = useState(0)

    useEffect(()=>{
        axios(baseUrl.concat(idParam.id).concat("?page=").concat(page))
        .then(response => {
            console.log(response.data)
            setData(response.data)
        })
        .catch(err => console.log(err))
    },[])

    const handlePageClick = (page) =>{
        console.log("click")
        axios(baseUrl.concat(idParam.id).concat("?page=").concat(page.selected))
              .then(response => {
                  console.log(response.data)
                  setData(response.data)
              })
              .catch(err => console.log(err))
      }
      
      const handleCreateClick = ()=>{
        axios.post("http://localhost:8181/stores/orders/" + idParam.id)
        .then(response => console.log(response.status))
        .catch(err => console.log(err))
        window.location.reload(false)
      }

      const handleStatusSort = (event) =>{

        if(event.target.checked){
          let orders  = data.filter(order => order.status === "IN_PROCESSING")
          setData(orders)
        }
        else{
          window.location.reload(false)
        }
      }

    const OrdersList = data.map( (orderRow, index) => {
        return (
          <tr key = {index}>
            <td>{orderRow.id}</td>
            <td>{orderRow.createTime}</td>
            <td>{orderRow.status}</td>
            <td>
              <button type="button" className="btn btn-warning" onClick = {()=>navigate("/store/orders/" + orderRow.id)}>details</button>
            </td>
          </tr>
        )})

    return(
        <>
        <LayoutStore />
        <div className="container w-50">
        <h2>Orders</h2>
        <div className="form-check form-switch m-2">
          <input className="form-check-input" type="checkbox" id="flexSwitchCheckDefault" onChange={handleStatusSort}/>
          <label className="form-check-label" htmlFor="flexSwitchCheckDefault">Orders in processing</label>
        </div>
        <table className="table table-dark table-striped align-middle">
          <thead>
            <tr>
              <th scope="col">ID</th>
              <th scope="col">CREATE TIME</th>
              <th scope="col">STATUS</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {OrdersList}
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
            previousClassName={"page-item"} />
        </div>
        <div className="d-grid gap-2 col-6 mx-auto">
          <button className="btn btn-success" type="button" onClick = {handleCreateClick}>CREATE ORDER</button>
        </div>
      </div>
      </>
    )
}

export default StoreOrder;