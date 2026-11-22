import KpiCards from "../../components/KpiCard";
import Sagsoversigt from "../../components/Sagsoversigt/Sagsoversigt";
import { Heading } from "./style";

export function OverviewPage() {
  return (
    <>
      <Heading>Aktive sager</Heading>
      <KpiCards />
      <Sagsoversigt />
    </>
  );
}
