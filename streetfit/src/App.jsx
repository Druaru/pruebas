import { createBrowserRouter, RouterProvider } from "react-router-dom";
import HomePage from "./pages/Home.jsx";
import AboutUs from "./pages/AboutUs.jsx";
import Galery from "./pages/Galery.jsx";
import ContactUs from "./pages/ContactUs.jsx";

import RootLayout from "./pages/Root.jsx";
import Footer from "./components/Footer.jsx";
import Login from "./components/Login.jsx";
import Register from "./components/Register.jsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    children: [
      { path: "/", element: <HomePage /> },
      { path: "/about", element: <AboutUs /> },
      { path: "/galery", element: <Galery /> },
      { path: "/contactus", element: <ContactUs /> },
      { path: "/login", element: <Login /> },
      { path: "/register", element: <Register /> },
    ],
  },
]);

function App() {
  return (
    <div>
      <RouterProvider router={router} />
      <Footer />
    </div>
  );
}

export default App;
