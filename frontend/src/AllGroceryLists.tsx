import {useEffect, useState} from "react";
import axios from "axios";
import type {GroceryList} from "./model/GroceryList.tsx";

export default function AllGroceryLists() {

    const [groceryList, setGroceryList] = useState<GroceryList[]>([]);

    function getAllGroceryLists() {
        axios.get("api/grocery-list")
            .then((gl) => setGroceryList(gl.data))
            .catch(e => console.log(e))
    }

    useEffect(() => {
        getAllGroceryLists()
    }, []);

    return (
        <>
            <h2>Your grocery lists:</h2>

            {groceryList.map(list => (
                <div key={list.id}>
                    <h3>{list.id} - {list.status}</h3>
                    <ul>
                        {list.products.map(productList => (
                            <li key={productList.product.name}>{productList.product.name} - {productList.quantity}</li>
                        ))}
                    </ul>

                </div>
            ))}

        </>
    )

}