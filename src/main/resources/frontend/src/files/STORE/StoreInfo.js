import { useEffect, useState } from "react"
import {useNavigate, useParams} from "react-router-dom"
import axios from "axios";

const baseUrl = 'http://localhost:8181/stores/'

const StoreInfo = () => {

    const idParam = useParams()
    const navigate = useNavigate()

    const [data, setData] = useState([])
    const [sellers, setSellers] = useState([])

    useEffect(()=>{
        axios(baseUrl.concat(idParam.id))
        .then(response => {
            console.log(response.data)
            setData(response.data)
            setSellers(response.data.sellers)
        })
        .catch(err => console.log(err))
    },[])

    const SellerList = sellers.map((seller, index) => {
        return(
            <ul  key={index}>
                <li>{seller.firstName} {seller.lastName} {seller.email}</li>
            </ul>
        )})
    
    return(
        <div className="container card w-50">

            <div className="card-body" >
                <h5 className="card-title">{idParam.id} {data.name}</h5>
                <p>Open date: {data.openDate}</p>
                <h6>Selles</h6>
                {SellerList}
                <button className="btn btn-info" onClick={()=>navigate("/users/password/")}>Update password</button>
            </div>
        </div>
    )
}

export default StoreInfo;