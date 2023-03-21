import React from "react";

export default function TopProducts(topicData){

    return(
        
        <div className="container w-50">
            <h4>Top Products</h4>
            <table className="table table-striped align-middle">
                <thead>
                    <tr className="table-primary">
                        <th scope="col">ID</th>
                        <th scope="col">Product</th>
                        <th scope="col">Income</th>
                    </tr>
                </thead>
                <tbody>
                       {
                        topicData.data.map((s, index) => {

                            return(
                                <tr key = {index}>
                                    <td>{s.id}</td>
                                    <td>{s.name}</td>
                                    <td>{s.summaryIncome}</td>
                                </tr>
                            )})
                       }
                </tbody>
            </table>
        </div>
    )
}