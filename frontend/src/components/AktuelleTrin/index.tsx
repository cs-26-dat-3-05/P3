import { Card, StepHeader, HeaderLabel, HeaderTitle, StepRow, StepCircle, StepText, StepTitle, StepDescription, StepDate} from "./style"
export interface Step {
    number: number;
    title: string;
    description?: string;
    date?: string;
    status: "done" | "current" | "upcoming";
}

const MOCK_STEPS: Step[] = [
    {
        number: 1,
        title: "Returnering registreret",
        description: "Registreret automatisk fra Danske Banks kontoudtog",
        date: "18.06.2025",
        status: "done",
    },
    {
        number: 2,
        title: "Arrangør identificeret",
        description: "Kontaktoplysninger kontrolleret, herunder e-mail",
        date: "18.06.2025",
        status: "done",
    },
    {
        number: 3,
        title: "Nye bankoplysninger efterspurgt",
        description: "Automatisk mail sendt",
        date: "18.06.2025",
        status: "done",
    },
    {
        number: 4,
        title: "Afventer korrekt kontonummer",
        status: "current",
    },
    {
        number: 5,
        title: "Nye oplysninger indtastet",
        status: "upcoming",
    },
    {
        number: 6,
        title: "Udbetaling genindsendt",
        status: "upcoming",
    }
];

export default function AktuelleTrin({ step, progress }: { step: string; progress: number }) {
    const totalSteps = MOCK_STEPS.length;
    return (
        <Card>
            <StepHeader>
                <div>
                    <HeaderLabel>AKTUELLE TRIN</HeaderLabel>
                    <HeaderTitle>{step}</HeaderTitle>
                </div>
                <span>TRIN {progress} / {totalSteps}</span>
            </StepHeader>
            
            {MOCK_STEPS.map((item, index) => {
                const status = index + 1 < progress ? "done" : index + 1 === progress ? "current" : "upcoming";
                return (
                    <StepRow key={item.number}>
                        <StepCircle $status={status}>{status === "done" ? "✓" : item.number}</StepCircle>
                        <StepText>
                            <StepTitle $status={status}>{item.number === progress ? step : item.title}</StepTitle>
                            <StepDescription>{item.description}</StepDescription>
                        </StepText>
                        <StepDate>{item.date}</StepDate>
                    </StepRow>
                );
            })}
        </Card>
    );
}
