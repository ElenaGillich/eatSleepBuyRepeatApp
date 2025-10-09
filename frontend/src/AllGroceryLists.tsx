import {useEffect, useState} from "react";
import axios from "axios";
import type {GroceryList} from "./model/GroceryList.tsx";

export default function AllGroceryLists() {

    const [groceryList, setGroceryList] = useState<GroceryList[]>([]);

    function getAllGroceryLists() {
        axios.get("api/grocery-list")
            .then((gl) => {
                console.log("Response data: ", gl.data)
                setGroceryList(gl.data)
            })
            .catch(e => console.log(e))
    }

    function handleDelete(id: string) {
        console.log("Trying to delete ID:", id);
        axios.delete("api/grocery-list/" + id)
            .then(() => {
                setGroceryList(lists => lists.filter((list) => list.id !== id));
            })
            .catch(e => console.log(e))
    }

    useEffect(() => {
        getAllGroceryLists()
    }, []);

    return (
        <>
            <h2>Your grocery lists:</h2>

            {groceryList.length > 0 ? (
                groceryList.map(list => (
                <div key={list.id}>
                    <h3>{list.id} - {list.status}</h3>
                    <ul>
                        {list.products.map(productList => (
                            <li key={productList.product.name}>{productList.product.name} - {productList.quantity}</li>
                        ))}
                    </ul>
                    <button onClick={() => handleDelete(list.id)}>🗑️ Delete</button>
                </div>
            ))) : (
                <p>No grocery lists found...</p>
            )
            }

        </>
    )

}