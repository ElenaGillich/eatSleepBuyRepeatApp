import {useEffect, useState} from "react";
import axios from "axios";
import type {GroceryList} from "./model/GroceryList.tsx";
import {useNavigate} from "react-router-dom";

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

            <div className={"add-grocery"}>
                <button onClick={() => nav("/addGroceryList")}>
                    Add new grocery list
                </button>
            </div>

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