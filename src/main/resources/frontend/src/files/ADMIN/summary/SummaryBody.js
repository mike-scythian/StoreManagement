import React from "react";

export default function SummaryBody(data){

    return(
        <tbody>
        {
            data.data.map((s, index) => {

            return(
                <tr key = {index}>
                    <td>{s.id}</td>
                    <td>{s.product}</td>
                    <td>{s.payment}</td>
                    <td>{s.timeOperation}</td>
                    <td>{s.store}</td>
                </tr>
            )})
        }
        </tbody>
    )
}