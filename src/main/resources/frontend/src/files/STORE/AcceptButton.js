import React from "react";
import axios from "axios";

const orderUrl = 'http://localhost:8181/stores/accept/'

export default function AcceptButton(id){

    const acceptClick =() => {

        console.log(id)

         axios.put(orderUrl.concat(id.orderId),[])
                       .then(response => {
                           console.log(response.data);
                           window.location = "/orders/store/"+ id.storeId
                     })
                     .catch(err => console.log(err))
       }

    return(
        <>
            <button type="button" className="btn btn-warning m-3" onClick={() => acceptClick()}>ACCEPT</button>
        </>
    )

}