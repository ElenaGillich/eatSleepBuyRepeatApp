import {useEffect, useState} from "react";
import axios from "axios";
import type {GroceryList} from "./model/GroceryList.tsx";
import {useNavigate} from "react-router-dom";
import './AllGroceryLists.css';

export default function AllGroceryLists() {

    const [groceryList, setGroceryList] = useState<GroceryList[]>([]);
    const nav = useNavigate();

    const [searchId, setSearchId] = useState<string>("");

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

    function handleIdSearch() {
        if (!searchId.trim()) {
            return getAllGroceryLists();
        }
        axios
            .get(`/api/grocery-list/${searchId}`)
            .then((res) => setGroceryList([res.data]))
            .catch((err) => {
                console.error("List of this ID not found", err, searchId);
                alert("List of this ID not found");
            });
    }

    return (
        <>
            <div>
                <label> Search by ID:
                    <input type={"text"} placeholder={"Enter ID here"} value={searchId}
                           onChange={(e) => {
                               setSearchId(e.target.value);
                               if (!e.target.value) {
                                   return getAllGroceryLists();
                               }
                           }}/>
                </label>
                <button onClick={handleIdSearch}>Search</button>
            </div>

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