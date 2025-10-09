import {useEffect, useState} from "react";
import axios from "axios";
import type {GroceryList} from "./model/GroceryList.tsx";
import {useNavigate} from "react-router-dom";
import './AllGroceryLists.css';

export default function AllGroceryLists() {

    const [groceryList, setGroceryList] = useState<GroceryList[]>([]);
    const nav = useNavigate();

    function getAllGroceryLists() {
        axios.get("api/grocery-list")
            .then((gl) => {
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
            <div className={"main-page-for-all-lists"}>
                <div className={"add-grocery"}>
                    <button onClick={() => nav("/addGroceryList")}>
                        Add new grocery list
                    </button>
                </div>

                <h2>Your grocery lists:</h2>
                <div className={"cards-columns"}>
                    {groceryList.length > 0 ? (
                        groceryList.map(list => (
                            <div key={list.id} className={"grocery-list-card"}>
                                <h3 className={"grocery-list-card-text"}>{list.id} - {list.status}</h3>
                                <ul className={"grocery-list-card-inner"}>
                                    {list.products.map(productList => (
                                        <div key={productList.product.name}
                                             className={"grocery-list-card-inner-elements"}>{productList.product.name} - {productList.quantity}</div>
                                    ))}
                                </ul>
                                <button className={"grocery-list-card-button"} onClick={() => handleDelete(list.id)}>🗑️ Delete</button>
                            </div>
                        ))) : (
                        <p>No grocery lists found...</p>
                    )
                    }
                </div>
            </div>
        </>
    )

}