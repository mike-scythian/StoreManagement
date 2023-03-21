import React from "react";
import axios from "axios";

const orderUrl = 'http://localhost:8181/orders/'

export default function PushButton(id){

    const pushClick =() => {
        console.log(id)
         axios.put(orderUrl +"push/" + id.id,[])
                       .then(response => {
                           console.log(response.data);
                           window.location = "/orders"
                     })
                     .catch(err => console.log(err))
       }

    return(
        <>
            <button type="button" className="btn btn-success m-3" onClick={() => pushClick()}>PUSH</button>
        </>
    )

}