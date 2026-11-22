import { Routes, Route } from "react-router";
import Header from "./components/Header";
import CasePage from "./pages/CasePage";
import { OverviewPage } from "./pages/OverviewPage";

export default function App() {
  return (
    <>
      <Header />
      <main className="page">
        <Routes>
          <Route path="/" element={<OverviewPage />} />
          <Route path="/cases/:caseId" element={<CasePage />} />
        </Routes>
      </main>
    </>
  );
}
